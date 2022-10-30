

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
    var res: Int = Buhgalter.getMonthlyRate(12000, 20, 5)


  }


  /*
  * с.     Напишите программу, которая рассчитывает для каждого сотрудника отклонение(в процентах) от среднего значения оклада на уровень всего отдела. В итоговом значении должно учитываться в большую или меньшую сторону отклоняется размер оклада. На вход вышей программе подаются все значения, аналогичные предыдущей программе, а также список со значениями окладов сотрудников отдела 100, 150, 200, 80, 120, 75.

  d.      Попробуйте рассчитать новую зарплату сотрудника, добавив(или отняв, если сотрудник плохо себя вел) необходимую сумму с учетом результатов прошлого задания. Добавьте его зарплату в список и вычислите значение самой высокой зарплаты и самой низкой.

  e.     Также в вашу команду пришли два специалиста с окладами 350 и 90 тысяч рублей. Попробуйте отсортировать список сотрудников по уровню оклада от меньшего к большему.

  f.     Кажется, вы взяли в вашу команду еще одного сотрудника и предложили ему оклад 130 тысяч. Вычислите самостоятельно номер сотрудника в списке так, чтобы сортировка не нарушилась и добавьте его на это место.

  g.       Попробуйте вывести номера сотрудников из полученного списка, которые попадают под категорию middle. На входе программе подается «вилка» зарплаты специалистов уровня middle.

  h.     Однако наступил кризис и ваши сотрудники требуют повысить зарплату. Вам необходимо проиндексировать зарплату каждого сотрудника на уровень инфляции – 7%
  * */
}




//@main def Runner(args: String*): Unit = {
//  println("Hello, World!")
//}


def addInt(a: Int, b: Int): Int = {
  var sum: Int = 0
  sum = a + b
  return sum
}