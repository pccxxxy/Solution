import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import java.util.Date

class test extends AnyFlatSpec{

  // To set up Spark library
  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("UnitTest")
    .getOrCreate();

  // Import dataset
  val flights = spark.read.option("header",true).option("inferSchema",true).csv("/Users/dufflelee/Solution/data/flightData.csv")
  val passengers = spark.read.option("header",true).option("inferSchema",true).csv("/Users/dufflelee/Solution/data/passengers.csv")

  // Test script for q1
  it should "match_Q1" in{
    val expected = 94

    val df = tasks.totalFlightsEachMonth(flights).where("Month = 12")
      .select("Number of Flights")
      .collectAsList()
    val actual: Int = df.get(0)(0).toString.toInt

    assert(expected == actual)
  }

  // Test script for q2
  it should "check_count_Q2" in{
    val expected = 100

    val actual = tasks.findHundredMostFreqFlyers(flights, passengers )
      .count()

    assert(expected == actual)
  }

  // Test script for q3
  it should "match_Q3" in{
    val expected = 17

    val df = tasks.findGreatestCountries(flights, spark).where("`Passenger ID` = 1337")
      .select("Longest Run")
      .collectAsList()
    val actual: Int = df.get(0)(0).toString.toInt

    assert(expected == actual)
  }

  // Test script for q4
  it should "match_Q4" in{
    val expected = 15

    val df = tasks.moreThanThreeFlightsInCommon(flights)
      .select("Number of flights together")
      .collectAsList()
    val actual: Int = df.get(0)(0).toString.toInt

    assert(expected == actual)
  }

  // Test script for q5
  it should "match_Q5" in{
    val atLeastNTimes = 5

    val from = new Date("2017/05/02")
    val to = new Date("2017/06/29")

    val df = tasks.flownTogether(flights, atLeastNTimes, from, to)
    df.createOrReplaceTempView("data")

    val result = spark.sql(
      """select min(from) as from, max(to) as to,
        |min(`Number of flights together`) as flights from data""".stripMargin).collect()

    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    val fromDate = format.format(from)
    val toDate = format.format(to)

    assert(atLeastNTimes != result(0)(2) && (fromDate == result(0)(0)) && (toDate == result(0)(1)))
  }
}
