package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.cssSelector;

public class FormTest {

    @Test
    void shouldSubmitRequestAllValid() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitFormIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_text .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitNameIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234657891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_text .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitPhoneIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_tel .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotSubmitAgreementIsEmpty() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[role=button]")).click();
        $(".input_invalid [role=presentation]").shouldHave(Condition.text("Я соглашаюсь"));
    }

    @Test
    void shouldSubmitRequestNameInCaps() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("ОЛЕГ КЛИМОВ");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitRequestNameWithHyphen() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Ли-ху");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldNotSubmitRequestNameInLatinLetters() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Oleg Klimov");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_text .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitNameInvalidSymbols() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("@лег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("+71234567891");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_text .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNotSubmitRequestPhoneIs1Number() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("8");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_tel .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNotSubmitRequestPhoneIs10Numbers() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(cssSelector("[data-test-id=name] input")).sendKeys("Олег Климов");
        form.$(cssSelector("[data-test-id=phone] input")).sendKeys("8123456709");
        form.$(cssSelector("[data-test-id=agreement]")).click();
        form.$(cssSelector("[role=button]")).click();
        $(".input_type_tel .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
