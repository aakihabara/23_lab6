<p align = "center">МИНИСТЕРСТВО НАУКИ И ВЫСШЕГО ОБРАЗОВАНИЯ<br>
РОССИЙСКОЙ ФЕДЕРАЦИИ<br>
ФЕДЕРАЛЬНОЕ ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ<br>
ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ ВЫСШЕГО ОБРАЗОВАНИЯ<br>
«САХАЛИНСКИЙ ГОСУДАРСТВЕННЫЙ УНИВЕРСИТЕТ»</p>
<br><br><br><br><br><br>
<p align = "center">Институт естественных наук и техносферной безопасности<br>Кафедра информатики<br>Коньков Никита Алексеевич</p>
<br><br><br>
<p align = "center">Лабораторная работа №6<br><strong>«Версии Android SDK и совместимость».</strong><br>01.03.02 Прикладная математика и информатика</p>
<br><br><br><br><br><br><br><br><br><br><br><br>
<p align = "right">Научный руководитель<br>
Соболев Евгений Игоревич</p>
<br><br><br>
<p align = "center">г. Южно-Сахалинск<br>2022 г.</p>
<br><br><br><br><br><br><br><br><br><br><br><br>

<h1 align = "center">Введение</h1>

<p><b>Android Studio</b> — интегрированная среда разработки (IDE) для работы с платформой Android, анонсированная 16 мая 2013 года на конференции Google I/O. В последней версии Android Studio поддерживается Android 4.1 и выше.</p>
<p><b>Kotlin</b> — это кроссплатформенный статически типизированный язык программирования общего назначения высокого уровня. Kotlin предназначен для полного взаимодействия с Java, а версия стандартной библиотеки Kotlin для JVM зависит от библиотеки классов Java, но вывод типов позволяет сделать ее синтаксис более кратким. Kotlin в основном нацелен на JVM, но также компилируется в JavaScript (например, для интерфейсных веб-приложений, использующих React) или собственный код через LLVM (например, для собственных приложений iOS, разделяющих бизнес-логику с приложениями Android). Затраты на разработку языка несет JetBrains, а Kotlin Foundation защищает торговую марку Kotlin.</p>

<br>
<h1 align = "center">Цели и задачи</h1>
<p>1. <b> Упражнение. Вывод версии Android на устройстве. </b> Добавьте в макет GeoQuiz виджет TextView для вывода уровня API устройства, на котором работает программа. Результат упражнения Задать текст TextView в макете не получится, потому что версия операционной системы устройства не известна до момента выполнения. Найдите функцию TextView для задания текста в справочной странице TextView в документации Android. Вам нужна функция, получающая один аргумент — строку (или CharSequence). Для настройки размера и гарнитуры текста используйте атрибуты XML, перечисленные в описании TextView. </p>
<p>2. <b> Упражнение. Ограничение подсказок.  </b> Ограничьте пользователя тремя подсказками. Храните информацию о том, сколько раз пользователь подсматривал ответ, и выводите количество оставшихся подсказок под кнопкой. Если ни одной подсказки не осталось, то кнопка получения подсказки блокируется. </p>

<p><a href="https://www.codewars.com/kata/59377c53e66267c8f6000027">3. Alphabet war</a></p>
<p><a href="https://www.codewars.com/kata/63b84f54693cb10065687ae5">4. The 'spiraling' box</a></p>
<p><a href="https://www.codewars.com/kata/566fc12495810954b1000030">5. Count the Digit</a></p>
<p><a href="https://www.codewars.com/kata/5b180e9fedaa564a7000009a">6. Fix string case</a></p>
<p><a href="https://www.codewars.com/kata/55caef80d691f65cb6000040">7. Geometric Progression Sequence</a></p>
<p><a href="https://www.codewars.com/kata/55fd2d567d94ac3bc9000064">8. Sum of odd numbers</a></p>

<h1 align = "center">Решение</h1>

<p>В файл Main Activity я добавил:</p>

```Kotlin
apiView.setText("API level " + android.os.Build.VERSION.SDK_INT.toString())
```

<p>Благодаря этой строке я в поле apiView выводу информацию о текущей версии API</p>

<p>Также для ограничения подсказок я добавил функцию, которая проверяет, на сколько вопросов пользователь уже получил ответ с помощью CheatActivity</p>

```Kotlin
    private fun checkCheatTries(){

        var counting = 0

        for (i in quizViewModel.isCheated){
            if(i){
                counting++
            }
        }

        if(quizViewModel.allTries - counting <= 0){
            cheatButton.isEnabled = false
        }

    }
```

<h2 align = "center">Codewars</h2>

<h2 align = "center"><a href="https://www.codewars.com/users/akihabara">Аккаунт на codewars</a></h2>

<h2 align = "center">Alphabet war</h2>

```kotlin
fun alphabetWar(fight: String): String {
    var leftSide = 0
    var rightSide = 0
    for(i in fight){
        when(i){
            'w' -> leftSide += 4
            'p' -> leftSide += 3
            'b' -> leftSide += 2
            's' -> leftSide++
            'm' -> rightSide += 4
            'q' -> rightSide += 3
            'd' -> rightSide += 2
            'z' -> rightSide++
        }
    }
    
    if(leftSide > rightSide){
        return "Left side wins!"
    } else if (leftSide == rightSide){
        return "Let's fight again!"
    } else {
        return "Right side wins!"
    }
}
```

<h2 align = "center">The 'spiraling' box</h2>

```kotlin
fun createBox(width: Int, length: Int): Array<IntArray> {
    val matr = Array(length){ IntArray(width) }
    var startRow = 0
    var startCol = 0
    var endRow = length - 1
    var endCol = width - 1
    var num = 1
    
    while(startRow <= endRow && startCol <= endCol){
        
        for( col in startCol .. endCol){
            matr[startRow][col] = num
            matr[endRow][col] = num
        }
        startRow++
        endRow--
        
        for(row in startRow..endRow){
            matr[row][startCol] = num
            matr[row][endCol] = num
        }
        startCol++
        endCol--
        
        num++
    }
    
    return matr
}
```

<h2 align = "center">Count the Digit</h2>

```kotlin
package countdig

fun nbDig(n:Int, d:Int):Int {
    var res = 0    
   
    for (i in 0..n){
        var temp = i * i
        while (temp != 0) {
          if (temp % 10 == d)
          res++
          temp /= 10;
        }        
    }
    
    if (d == 0) {
        res++
    }
    
    return res
}
```

<h2 align = "center">Fix string case</h2>

```kotlin
object FixStringCase {
    fun solve(s: String): String = if (s.count { it.isLowerCase() } * 2 >= s.length) s.toLowerCase() else s.toUpperCase()
}
```

<h2 align = "center">Geometric Progression Sequence</h2>

```kotlin
fun geometricSequenceElements(a: Int, r: Int, n: Int): String{
        var res = ""
        var num = a
        for (i in 1 until n) {
            if (i == 1) res += a.toString()
            num *= r
            res += ", " + num.toString()
        }
        return res
}
```

<h2 align = "center">Sum of odd numbers</h2>

```kotlin
fun rowSumOddNumbers(n: Int): Int {
    var res = n * n * n
    return res
}
```



<h1 align = "center">Вывод</h1>
<p>По итогу проделанной лабораторной работы, я добавил ограничение попыток, научился получать информацию по версии API устройства, прорешал ряд задач на платформе Codewars. </p>