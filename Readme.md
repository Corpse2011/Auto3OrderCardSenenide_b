

# Домашнее задание к занятию «2.1. Тестирование веб-интерфейсов»

В качестве результата пришлите ссылку на ваш GitHub-проект в личном кабинете студента на сайте [netology.ru](https://netology.ru).

Все задачи этого занятия нужно делать в одном репозитории.

**Важно**: если у вас что-то не получилось, то оформляйте Issue [по установленным правилам](../report-requirements.md).

**Важно**: не делайте ДЗ всех занятий в одном репозитории! Иначе вам потом придётся достаточно сложно подключать системы Continuous Integration.

## Как сдавать задачи

1. Инициализируйте на своём компьютере пустой Git-репозиторий
1. Добавьте в него готовый файл [.gitignore](../.gitignore)
1. Добавьте в этот же каталог код ваших авто-тестов
1. Сделайте необходимые коммиты
1. Добавьте в каталог `artifacts` целевой сервис ([`app-order.jar`](app-order.jar) - см. раздел Настройка CI)
1. Создайте публичный репозиторий на GitHub и свяжите свой локальный репозиторий с удалённым
1. Сделайте пуш (удостоверьтесь, что ваш код появился на GitHub)
1. Удостоверьтесь, что на Appveyor сборка зелёная
1. Поставьте бейджик сборки вашего проекта в файл README.md
1. Ссылку на ваш проект отправьте в личном кабинете на сайте [netology.ru](https://netology.ru)
1. Задачи, отмеченные, как необязательные, можно не сдавать, это не повлияет на получение зачета

## Селекторы

Перед выполнением ДЗ рекомендуем вам ознакомиться с [кратким руководством по работе с селекторами](selectors.md).

## Настройка

### 1. Целевой сервис

Ваш целевой сервис (SUT - System under test), расположен в файле `app-order.jar` (в этом репозитории). Вам нужно его скачать и положить в каталог `artifacts` вашего проекта.

Поскольку файлы с расширением `.jar` находят в списках `.gitignore` вам нужно принудительно заставить git следить за ним: `git add -f artifacts/app-order.jar`.

После чего сделать `git push`. Обязательно удостоверьтесь, что файл попал в репозиторий.

### 2. `build.gradle`

Ваш `build.gradle` должен выглядеть следующим образом:

```groovy
plugins {
    id 'java'
}

group 'ru.netology'
version '1.0-SNAPSHOT'

sourceCompatibility = 11

// кодировка файлов (если используете русский язык в файлах)
compileJava.options.encoding = "UTF-8"
compileTestJava.options.encoding = "UTF-8"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.6.1'
    testImplementation 'com.codeborne:selenide:5.19.0'
}

test {
    useJUnitPlatform()
    // в тестах, вызывая `gradlew test -Dselenide.headless=true` будем передавать этот параметр в JVM (где его подтянет Selenide)
    systemProperty 'selenide.headless', System.getProperty('selenide.headless')
}
```

#### **HEADLESS режим браузера**

На серверах сборки чаще всего нет графического интерфейса, поэтому запуская браузер в режиме **headless** мы отключаем графический интерфейс (при этот все процессы браузера продолжают работать так же).
При использовании **selenium** данный режим настраивается непосредственно в коде во время инициализации драйвера, примеры инициализации ниже.

Детальнее можете почитать про Headless:
- [Chrome](https://www.chromestatus.com/features/5678767817097216)
- [Gecko (Firefox)](https://developer.mozilla.org/en-US/docs/Mozilla/Firefox/Headless_mode)

Включение **headless** режима при использовании **selenium** необходимо реализовать в коде во время создания экземпляра webdriver:

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-dev-shm-usage");
options.addArguments("--no-sandbox");
options.addArguments("--headless");
driver = new ChromeDriver(options);
```

Для **selenide** **headless** режим активируется при запуске тестов с определенным параметром:
```
gradlew test -Dselenide.headless=true
```

#### **Webdriver для разных операционных систем**

Если вы выполняете работу с использованием **selenium**, то будьте готовы, что ваша сборка может упасть из-за того, что у вас в репозитории webdriver для одной ОС (например, для Windows), а в CI используется Linux. Для решения этой проблемы можно использовать библиотеку [webdriver manager](https://github.com/bonigarcia/webdrivermanager). Она автоматически определяет ОС и версию браузера, скачивает и устанавливает подходящий файл драйвера. Кстати, в Selenide используется именно эта библиотека.

### 3. `.appveyor.yml`

AppVeyor настраивается аналогично предыдущей лекции за исключением того, что тесты нужно запускать так, чтобы **selenide** запускался в headless-режиме (читайте выше про передачу специального флага при вводе команды запуска). Если тесты написаны с использованием **selenium**, то после включения headless режима в коде никаких дополнительных флагов в командной строке передавать не нужно.

## Задача №1 - Заказ карты

Вам необходимо автоматизировать тестирование формы заказа карты:

![](pic/order.png)

Требования к содержимому полей:
1. Поле Фамилия и имя - разрешены только русские буквы, дефисы и пробелы.
2. Поле телефон - только цифры (11 цифр), символ + (на первом месте).
3. Флажок согласия должен быть выставлен.

Тестируемая функциональность: отправка формы.

Условия: если все поля заполнены корректно, то вы получаете сообщение об успешно отправленной заявке:

![](pic/success.jpg)

Вам необходимо самостоятельно изучить элементы на странице, чтобы подобрать правильные селекторы.

<details>
    <summary>Подсказка</summary>

    Смотрите на `data-test-id` и внутри него ищите нужный вам `input` - используйте вложенность для селекторов.
</details>

Проект с авто-тестами должен быть выполнен на базе Gradle, с использованием Selenide/Selenium (по выбору студента).

Для запуска тестируемого приложения скачайте jar-файл из текущего каталога и запускайте его командой:
`java -jar app-order.jar`

Приложение будет запущено на порту 9999.

Если по каким-то причинам порт 9999 на вашей машине используется другим приложением, используйте:

`java -jar app-order.jar -port=7777`

Убедиться, что приложение работает, вы можете открыв в браузере страницу: http://localhost:9999

## Задача №2 - Проверка валидации (необязательная)

После того, как вы протестировали Happy Path, необходимо протестировать остальные варианты.

Тестируемая функциональность: валидация полей перед отправкой.

Условия: если какое-то поле не заполнено, или заполнено неверно, то при нажатии на кнопку "Продолжить" должны появляться сообщения об ошибке (будет подсвечено только первое неправильно заполненное поле):

![](pic/error.png)

<details>
    <summary>Подсказка</summary>

    У некоторых элементов на странице появится css-класс `input_invalid`.
</details>