
import scala.math.round

object Buhgalter {

  /**
   * вычисляет ежемесячный оклад сотрудника после вычета налогов.
   * На вход вашей программе подается значение годового дохода до вычета налогов, размер премии –
   * в процентах от годового дохода и компенсация питания.
   *
   * @param yearRate
   * @param premiumPercent
   * @param foodCompensationPercent
   * @return
   */
  def getMonthlyRate(yearRate: Int, premiumPercent: Int, foodCompensationPercent: Int): Int = {
    val taxPercent = 13
    val monthQty = 12

    val percentOfYearRate = round(yearRate.toFloat / 100)
    val totalYearRate = yearRate
      + premiumPercent * percentOfYearRate
      + foodCompensationPercent * percentOfYearRate

    var monthlyRate: Float = totalYearRate / monthQty
    round(monthlyRate)
  }

  def getFinalStuffRates(premiumPercent: Int,
                         foodCompensationPercent: Int,
                         stuffRates: List[Int]): List[Int] = {
    stuffRates.map(x => {
      val foodCompensation = round(x.toFloat / 100) * foodCompensationPercent
      val premium = round(x.toFloat / 100) * premiumPercent
      x + foodCompensationPercent + premiumPercent
    })
  }

  /**
   * рассчитывает для каждого сотрудника
   * отклонение(в процентах) от  на уровень всего отдела.
   * В итоговом значении должно учитываться в большую или меньшую сторону отклоняется
   * размер оклада.   На вход вышей
   * программе подаются все значения, аналогичные предыдущей программе, а также список
   * со значениями окладов сотрудников отдела 100, 150, 200, 80, 120, 75.
   *
   * @param premiumPercent
   * @param foodCompensationPercent
   * @param stuffRates
   * @return
   */
  def getDeviationPercent(premiumPercent: Int,
                          foodCompensationPercent: Int,
                          stuffRates: List[Int]): (List[Int], Int) = {

    val finalStuffRates = getFinalStuffRates(premiumPercent, foodCompensationPercent, stuffRates)

    val meanStuffRate = {
      var sum: Int = 0
      finalStuffRates.map(x => sum += x)
      sum / stuffRates.size
    }

    var deviations: List[Int] = finalStuffRates.map(x => {
      val deviationPercent = round(x.toFloat / meanStuffRate.toFloat * 100)
      deviationPercent - 100
    })

    (deviations, meanStuffRate)
  }

  /**
   * Добавляет значение к зарплате по индексу
   * @param finalStuffRates
   * @param rateNum
   * @param summa
   * @return
   */
  def addSumToRateByNumber(finalStuffRates: List[Int], rateNum: Int, summa: Int):
  List[Int] = {
    if (!finalStuffRates.lift(rateNum).isDefined) {
      println(f"нет сотрудника с номером $rateNum%d - номер должен быть 0-${finalStuffRates.size}%d ")
      return finalStuffRates
    }

    val rate = finalStuffRates.lift(rateNum).get
    finalStuffRates.updated(rateNum, rate + summa)
  }

  def addToSortedList(lst: List[Int], number: Int):
  List[Int] = {
      var isAdded = false
    var extendedSortedLst:List[Int] = List()
    lst.map(x=> {
      if (!isAdded && number<=x) {
        extendedSortedLst = extendedSortedLst ::: List(number,x)
        isAdded = true
      } else {
        extendedSortedLst = extendedSortedLst :+ x
      }
    })
    extendedSortedLst
  }


}

