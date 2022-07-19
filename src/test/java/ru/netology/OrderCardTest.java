package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class OrderCardTest {
    @BeforeEach
    public void openForm() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldValid() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();//Галочка
        $("[type=button]").click();           //Кнопочка
        $("[data-test-id=order-success]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldInvalidName1() {
        $("[data-test-id=name] input").setValue("Protopop Акакий");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
        //ишью
    void shouldInvalidName2() {
        $("[data-test-id=name] input").setValue("Ёкарный Бабай");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        //$("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $("[data-test-id=order-success]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldInvalidName3() {
        $("[data-test-id=name] input").setValue("Екарный-Бабай");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldInvalidName4() {
        $("[data-test-id=name] input").setValue("Абдурахман ибн Хаттаб");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=order-success]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
         //ишью
    void shouldInvalidName5() {
        $("[data-test-id=name] input").setValue("Абдурахман");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        //$("[data-test-id=order-success]").shouldHave(exactText(" Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldWhenInvalidTel10() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("+7902123456");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWhenInvalidTel12() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("+790212345678");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWhenInvalidTelLetter1() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("+7902g234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWhenInvalidTelLetter2() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("-79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWhenInvalidTelLetter3() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("7+9021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldWhenInvalidTelLetter4() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("89021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldInvalidField2() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidField11() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldInvalidField12() {
        $("[data-test-id=name] input").setValue("Протопоп Акакий");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("[type=button]").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNoAgree() {
        $("[data-test-id=name] input").setValue("Японский Городовой");
        $("[data-test-id=phone] input").setValue("+79021234567");
        $("[type=button]").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}