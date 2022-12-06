import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{BooleanType, IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.functions.to_timestamp

import java.time.{LocalDateTime, ZoneOffset}
//import spark.sqlContext.implicits._
import java.sql.Timestamp
import java.time.Instant
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import scala.math.round

object master {
  def main(args: Array[String]): Unit = {
    println("Hello Scala")

    val sparkConf = new SparkConf()
      .setAppName("dheeraj-spark-demo")
      .setMaster("local")

    val spark = SparkSession
      .builder()
      .config(sparkConf)
      .getOrCreate()

    val ctx = spark.sparkContext

    //Создайте схему будущего фрейма данных. Схема должна включать следующие атрибуты:
    var dataStrings = List(
      "11, 1667627416, visit, 101, Sport, True",
      "12, 1667628426, move, 102, Polit, False",
      "11, 1667637446, scroll, 103, Medic, True",
      "13, 1667638424, click, 102, Polit, True",
      "11, 1667647526, move, 104, IT, True",
      "13, 1667648426, visit, 102, Medic, True",
      "13, 1667650426, scroll, 102, Polit, True",
      "13, 1667651436, click, 101, Sport, True",
      "13, 1667662424, scroll, 103, Medic, True",
      "12, 1667665726, move, 104, Sport, False",
      "12, 1667687444, click, 101, Polit, False",
      "14, 1667697422, scroll, 102, Sport, True",
      "12, 1667707444, visit, 101, Medic, False",
      "13, 1667827426, scroll, 103, Sport, True",
      "12, 1667927426, visit, 103, Polit, False",
      "14, 1668023426, visit, 104, IT, True",
      "12, 1668224426, scroll, 102, Sport, False",
      "11, 1668524426, move, 101, Polit, True",
      "11, 1668524430, click, 103, IT, True",
      "11, 1668627426, scroll, 103, Polit, True",
      "11, 1668637426, click, 101, Medic, True",
      "13, 1668727422, scroll, 104, Sport, True",
      "14, 1668827426, click, 104, Polit, True",
      "12, 1668927226, move, 102, Sport, False",
      "12, 1669027221, click, 102, IT, False",
      "16, 1669027244, click, 103, IT, True",
      "17, 1669027246, scroll, 101, IT, False",
      "19, 1669027248, click, 108, IT, False",
      "19, 1669027249, click, 109, IT, False",
    )


    // Create string rdd with data-strings
    var rawRDD = ctx.makeRDD(dataStrings)

    // Create rowRDD from strings
    var rowRDD = rawRDD.
      map(_.split(","))
      .map(attributes => Row(
        attributes(0).trim().toInt,
        { // convert string to timestamp
          val strTimeStamp = attributes(1).trim()
          var t = strTimeStamp.toLong * 1000
          var seconds = t / 1000000000;
          var nanos = 0;

          var tt = Instant.ofEpochSecond(seconds, nanos);
          var ttt = LocalDateTime.ofInstant(tt, ZoneOffset.UTC)
          Timestamp.valueOf(ttt)
        },
        attributes(2).trim(),
        attributes(3).trim().toInt,
        attributes(4).trim(),
        attributes(5).trim().toBoolean))

    // Define schema
    var schema = StructType(
      StructField("id", IntegerType, false) ::
        StructField("timestamp", TimestampType, false) ::
        StructField("type", StringType, false) ::
        StructField("page_id", IntegerType, false) ::
        StructField("tag", StringType, false) ::
        StructField("sign", BooleanType, false) ::
        Nil
    )

    // Create df with schema and data
    var df = spark.createDataFrame(rowRDD, schema)

    df.printSchema()

    //  Вывести топ-5 самых активных посетителей сайта
    println("топ-5 самых активных посетителей сайта")
    df.groupBy("id").count().sort(desc("count")).limit(5).show()


    // Посчитать процент посетителей, у которых есть ЛК
    val allUserQty = df.select("id").distinct().count()
    val userWithSignQty = df.select("id").where("sign == True").distinct().count()
    val userWithSignShare = if (userWithSignQty > 0) round(userWithSignQty.toFloat / allUserQty.toFloat * 100) else 0
    println(f"процент посетителей, у которых есть ЛК: $userWithSignShare%d")

    // Вывести топ -5 страниц сайта по показателю общего кол - ва кликов на данной странице
    println("топ -5 страниц сайта по показателю общего кол - ва кликов на данной странице:")
    df.select("page_id").where("type == 'click'").groupBy("page_id").count().sort(desc("count")).limit(5).show()


    //    ·Добавьте столбец к фрейму данных со значением временного диапазона
    //    в рамках суток с размером окна
    // All time-ranges
    val ranges = List((0, 4), (4, 8), (8, 12), (12, 16), (20, 0)) // 0-4
    val rand = new scala.util.Random

    // Create rowRDD with new column
    rowRDD = rawRDD.
      map(_.split(","))
      .map(attributes => Row(
        attributes(0).trim().toInt,
        {
          val strTimeStamp = attributes(1).trim()
          var t = strTimeStamp.toLong * 1000
          var seconds = t / 1000000000;
          var nanos = 0;

          var tt = Instant.ofEpochSecond(seconds, nanos);
          var ttt = LocalDateTime.ofInstant(tt, ZoneOffset.UTC)
          Timestamp.valueOf(ttt)
        },
        attributes(2).trim(),
        attributes(3).trim().toInt,
        attributes(4).trim(),
        attributes(5).trim().toBoolean,
        ranges(rand.nextInt(5)).toString()) // randomly add range
      )

    // extend schema
    schema = StructType(schema.fields.toArray :+ StructField("range", StringType, false))

    // Create extended df
    df = spark.createDataFrame(rowRDD, schema)

    //    ·Выведите временной промежуток на основе предыдущего задания
    //    , в течение которого было больше всего активностей на сайте.
    println("временной промежуток, в течение которого было больше всего активностей на сайте.")
    df.groupBy("range").count().sort(desc("count")).limit(1).show()

    //    ·Создайте второй фрейм данных
    //    , который будет содержать информацию о ЛК посетителя сайта со следующим списком атрибутов
    //
    //    1. Id    //    –уникальный идентификатор личного кабинета//
    //    2. User_id    //    –уникальный идентификатор посетителя
    //    3. ФИО посетителя
    //    4. Дату рождения посетителя
    //    5. Дата создания ЛК

    val userIds = List(11, 12, 13, 14, 16, 17, 19) // 0-3

    // Create rowRDD with new column
    rowRDD = rawRDD.
      map(_.split(","))
      .map(attributes => Row(
        // randomly get user-id
        rand.nextInt(Integer.SIZE - 1),
        userIds(rand.nextInt(3)),
        //    3. ФИО посетителя
        f" ${rand.nextString(5)}%s ${rand.nextString(4)}%s ${rand.nextString(8)}%s ",
        { //    4. Дату рождения посетителя
          var tt = Instant.ofEpochSecond(rand.nextInt(10000000) + 900000000, 0);
          var ttt = LocalDateTime.ofInstant(tt, ZoneOffset.UTC)
          Timestamp.valueOf(ttt)
        },
        { //    5. Дата создания ЛК
          var tt = Instant.ofEpochSecond(rand.nextInt(900000000) + 1100000000L, 0);
          var ttt = LocalDateTime.ofInstant(tt, ZoneOffset.UTC)
          Timestamp.valueOf(ttt)
        },
      ))

    schema = StructType(
      StructField("id", IntegerType, false) ::
        StructField("user_id", IntegerType, false) ::
        StructField("fio", StringType, false) ::
        StructField("date_of_birth", TimestampType, false) ::
        StructField("date_of_lk_creation", TimestampType, false) ::
        Nil
    )

    // Create df with schema and data
    var lkDf = spark.createDataFrame(rowRDD, schema)

    // Вывести фамилии посетителей, которые читали хотя бы одну новость про спорт
    var joinedDf = (df.as("df").join(lkDf, col("user_id") === col("df.id")))
    joinedDf.groupBy("user_id", "fio", "tag").count()
      .select("fio").filter(joinedDf("tag") === "Sport" && col("count") > 0).show()


  }
}
