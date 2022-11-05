
import scala.io.StdIn.readLine
import scala.math.round

object Main {
  val tstString: String = "Hello, Scala!"

  def main(args: Array[String]): Unit = {

    // a.i выводит фразу «Hello, Scala!» справа налево
    var resStr = StringManipulator.reverseString(tstString)
    println(resStr)

    // a.ii переводит всю фразу в нижний регистр
    resStr = StringManipulator.toLowerCaseString(tstString)
    println(resStr)

    // a.iii удаляет символ!
    resStr = StringManipulator.deleteCharFromString(tstString, '!')
    println(resStr)

    // a.iv добавляет в конец фразы «and goodbye python!»
    resStr = StringManipulator.addStrToEnd(tstString, "and goodbye python!")
    println(resStr)

    /* b. Напишите программу, которая вычисляет ежемесячный оклад сотрудника после вычета налогов.
    * На вход вашей программе подается значение годового дохода до вычета налогов, размер премии –
    * в процентах от годового дохода и компенсация питания.
    * */
    //
    println("введите годовой доход")
    var yearRate = readLine().toInt

    println("введите размер премии %")
    var premiumPercent = readLine().toInt

    println("введите размер компенсации на еду %")
    var foodPercent = readLine().toInt

    //    val yearRate = 1000
    //    val premiumPercent = 20
    //    val foodPercent = 5

    var res: Int = Buhgalter.getMonthlyRate(yearRate, premiumPercent, foodPercent)
    println(f"b. ежемесячный оклад: $res%s")


    /* с.     Напишите программу, которая рассчитывает для каждого сотрудника
    отклонение(в процентах) от среднего значения оклада на уровень всего отдела.
    В итоговом значении должно учитываться в большую или меньшую сторону отклоняется
    размер оклада.
    На вход вышей
    программе подаются все значения, аналогичные предыдущей программе, а также список
    со значениями окладов сотрудников отдела 100, 150, 200, 80, 120, 75.
     */
    val departmentsRates = List(100, 150, 200, 80, 120, 75)
    var (results, meanStuffRate) = Buhgalter.getDeviationPercent(premiumPercent, foodPercent, departmentsRates)

    println(f"c. отклонение(в процентах) от среднего значения $meanStuffRate%d прц:")
    results.map(x => println(f"$x%d"))

    /*  d. Попробуйте рассчитать новую зарплату сотрудника, добавив(или отняв, если
    сотрудник плохо себя вел) необходимую сумму с учетом результатов прошлого задания.
    Добавьте его зарплату в список и вычислите значение самой высокой зарплаты и самой
    низкой.    */

    println(f"введите номер сотрудника для изменение зп 0-${departmentsRates.size}%d")
    var stuffNum = readLine().toInt

    println("введите на сколько ему изменить зп")
    var summ = readLine().toInt


    //    var stuffNum = 3
    //    var summ = 50

    var finalRates: List[Int] = Buhgalter.getFinalStuffRates(premiumPercent, foodPercent, departmentsRates)
    finalRates = Buhgalter.addSumToRateByNumber(finalRates, stuffNum, summ)
    println("поменяли зп - рез-т такой")
    finalRates.map(println)
    var sortedRates = finalRates.sorted
    val maxRate = sortedRates.head
    val minRate = sortedRates.last
    println(f"мин зп $minRate%d")
    println(f"мах зп $maxRate%d")

    /*    e.     Также в вашу команду пришли два специалиста с окладами 350 и 90 тысяч рублей.
    Попробуйте отсортировать список сотрудников по уровню оклада от меньшего к большему. */
    val person01Rate = 350
    val person02Rate = 90
    sortedRates = sortedRates ::: List(person01Rate, person02Rate)
    sortedRates = sortedRates.sorted
    println("Отсортированный список с новыми сотрудниками:")
    sortedRates.map(println)

    /*f.     Кажется, вы взяли в вашу команду еще одного сотрудника и предложили ему оклад 130 тысяч.
    Вычислите самостоятельно номер сотрудника в списке так, чтобы сортировка не нарушилась и
    добавьте его на это место.
    */
    val person03Rate = 130
    var extendedSortedRates = Buhgalter.addToSortedList(sortedRates, person03Rate)
    println(f"Отсортированный список с еще одним новыми сотрудником $person03Rate%d:")
    extendedSortedRates.map(println)


    /* g. Попробуйте вывести номера сотрудников из полученного списка,
    которые попадают под категорию middle.
    На входе программе подается «вилка» зарплаты специалистов уровня middle. */

    println("введите мин зп для middle")
    val minMiddleRate = readLine().toInt

    println("введите макс зп для middle")
    val maxMiddleRate = readLine().toInt

    //    val minMiddleRate = 100
    //    val maxMiddleRate = 190

    println(f"middle разработчики:")
    var i = 0
    extendedSortedRates.map(x => {
      i += 1
      if (minRate < x && x < maxRate) {
        println(f"сотрудник под номером $i%d с зп $x%d - middle разработчик")
      }
    })

    /*h.     Однако наступил кризис и ваши сотрудники требуют повысить зарплату. Вам необходимо проиндексировать
    зарплату каждого сотрудника на уровень инфляции – 7% */
    println("Проиндексированные на 7% зп:")
    val indexedRate = (rate: Int) => {
      rate + round(rate.toFloat / 100 * 7)
    }
    extendedSortedRates = extendedSortedRates.map(x => {
      indexedRate(x)
    })
    extendedSortedRates.map(println)

  }

}

