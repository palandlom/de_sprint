object StringManipulator {

  /**
   * выводит фразу справа налево
   * @param str
   * @return
   */
  def reverseString (str: String): String = {
    val revStrAsList = (for (i <- str.length - 1 to 0 by -1) yield str(i))
    revStrAsList.mkString
  }

  /**
   * переводит str в нижний регистр
   * @param str
   * @return
   */
  def toLowerCaseString(str: String): String = {
    val revStrAsList = (for (ch <- str) yield ch.toLower)
    revStrAsList.mkString
  }

  /**
   * Удаляет символ delCh из строки
   * @param str
   * @param delCh
   * @return
   */
  def deleteCharFromString(str: String, delCh: Char): String = {
    val revStrAsList = (for (ch <- str  if (ch != delCh)) yield ch.toLower)
    revStrAsList.mkString
  }

  /**
   * Добавляет строку в конец
   * @param str
   * @param strToAdd
   * @return
   */
  def addStrToEnd(str: String, strToAdd: String): String = str + " " + strToAdd


}
