import org.apache.spark.sql.{DataFrame, SparkSession, functions}
import org.apache.spark.sql.functions.{col, collect_list, count, date_format, to_date}
import java.util.Date



object tasks {

  // Solution for q1
  def totalFlightsEachMonth(flights: DataFrame): DataFrame ={
    val result = flights.withColumn("Month", date_format(to_date(col("date"), "yyyy-MM-dd"), "MM"))
      .dropDuplicates("flightId","date")
      .groupBy("Month").count().withColumnRenamed("count","Number of Flights")
      .orderBy("Month")

    result
  }

  // Solution for q2
  def findHundredMostFreqFlyers(flights: DataFrame, passengers: DataFrame) = {
    val joined_df = flights.join(passengers, flights("passengerId") === passengers("passengerId"), "inner")
      .drop(flights("passengerId"))

    val result = joined_df.groupBy("passengerId", "firstName", "lastName").count()
      .select("passengerId", "count", "firstName", "lastName")
      .withColumnRenamed("passengerId", "Passenger ID")
      .withColumnRenamed("count","Number of Flights")
      .withColumnRenamed("firstName","First name")
      .withColumnRenamed("lastName","Last name")
      .orderBy(col("Number of Flights").desc).limit(100)

    result
  }

  // Solution for q3
  def totalCountries(list: Seq[String]): Int = {

    var newList: Set[String] = Set()
    var src = 0
    var dest = list.length / 2
    var max = 0

    while(dest < list.length){
      if(list(src) != "uk")
        newList += list(src)
      else{
        if(newList.size > max)
          max = newList.size
        newList = Set()
      }

      if(list(dest) != "uk")
        newList += list(src)
      else{
        if(newList.size > max)
          max = newList.size
        newList = Set()
      }

      dest = dest + 1
      src = src + 1
    }

    if(newList.size > max)
      max = newList.size

    max
  }

  def findGreatestCountries(flights: DataFrame, spark: SparkSession) = {

    val dataWithCountries = flights.orderBy("passengerId", "date").groupBy("passengerId")
      .agg(
        functions.concat(
          collect_list(col("from")),
          collect_list(col("to"))
        ).name("countries")
      )

    val numberOfCountryUDF = spark.udf.register[Int, Seq[String]]("number_of_country", totalCountries)

    val result = dataWithCountries.withColumn("Longest Run", numberOfCountryUDF(col("countries")))
      .orderBy(col("Longest Run").desc)
      .withColumnRenamed("passengerId", "Passenger ID")
      .select("Passenger ID", "Longest Run")

    result
  }

  // Solution for q4
  def moreThanThreeFlightsInCommon(flights: DataFrame) = {

    val flights1 = flights.withColumnRenamed("passengerId", "Passenger 1 ID")
    val flights2 = flights.withColumnRenamed("passengerId", "Passenger 2 ID")
    val result = flights1.join(flights2,
      flights1("Passenger 1 ID") < flights2("Passenger 2 ID")  &&
        flights1("flightId") === flights2("flightId") &&
        flights1("date") === flights2("date"),
      "inner"
    ).groupBy(flights1("Passenger 1 ID"), flights2("Passenger 2 ID")).
      agg(count("*").as("Number of flights together")).
      where("`Number of flights together` > 3")
      .orderBy(col("Number of flights together").desc)

    result
  }

  // Solution for q5
  def flownTogether(df: DataFrame, atLeastNTimes: Int, fromDate: Date, toDate: Date): DataFrame = {

    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    val from = format.format(fromDate)
    val to = format.format(toDate)

    val df1 = df.withColumnRenamed("passengerId", "Passenger 1 ID")
    val df2 = df.withColumnRenamed("passengerId", "Passenger 2 ID")
    val result = df1.join(df2,
      df1("Passenger 1 ID") < df2("Passenger 2 ID") &&
        df1("flightId") === df2("flightId") &&
        df1("date") === df2("date"),
      "inner"
    ).groupBy(df1("Passenger 1 ID"), df2("Passenger 2 ID")).
      agg(count("*").as("Number of flights together"),
        functions.min(df1("date")).as("from"),
        functions.max(df1("date")).as("to"))
      .where(s"""`Number of flights together` > ${atLeastNTimes} and from >= "${from}" and to <= "${to}" """)
      .orderBy(col("Number of flights together").desc)

    result
  }


  def main(args: Array[String]): Unit = {
    // To set up Spark library
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("tasks_v1")
      .getOrCreate();

    // To show ERRORS of spark set-up in logs
    spark.sparkContext.setLogLevel("ERROR")


    // Import dataset
    val flights = spark.read.option("header",true).option("inferSchema",true).csv("/Users/dufflelee/Solution/data/flightData.csv")
    val passengers = spark.read.option("header",true).option("inferSchema",true).csv("/Users/dufflelee/Solution/data/passengers.csv")


    // Call functions to query on the dataset for all 5 questions
    val Q1 = totalFlightsEachMonth(flights)
    Q1.write.mode("overwrite").option("header", "true").csv("/Users/dufflelee/Solution/answer/q1")
    Q1.show

    val Q2 = findHundredMostFreqFlyers(flights, passengers)
    Q2.write.mode("overwrite").option("header", "true").csv("/Users/dufflelee/Solution/answer/q2")
    Q2.show(100, false)

    val Q3 = findGreatestCountries(flights, spark)
    Q3.write.mode("overwrite").option("header", "true").csv("/Users/dufflelee/Solution/answer/q3")
    Q3.show(false)

    val Q4 = moreThanThreeFlightsInCommon(flights)
    Q4.write.mode("overwrite").option("header", "true").csv("/Users/dufflelee/Solution/answer/q4")
    Q4.show

    val from = new Date("2017/01/02")
    val to = new Date("2017/06/29")
    val Q5 = flownTogether(flights, 14, from, to)
    Q5.write.mode("overwrite").option("header", "true").csv("/Users/dufflelee/Solution/answer/q5")
    Q5.show
  }
}
