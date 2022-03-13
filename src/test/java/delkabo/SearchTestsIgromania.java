package delkabo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTestsIgromania {

    @BeforeEach
    void beforeEach() {
        open("https://www.igromania.ru/");
    }

    @AfterEach
    void closeWebBrowser() {
        closeWebDriver();
    }

    @Test
    @DisplayName("Простой тест")
    void simpleSearchTest() {

        $(".search_box_link").click();
        $(".ssearch_inp").setValue("DOOM (2016)");
        $(withText("DOOM (2016)")).click();
        $(".name-layers").shouldHave(text("DOOM (2016)"));
        $(byText("Рецензии на DOOM (2016)")).click();
        $(".name-layers").shouldHave(text("Рецензии DOOM (2016)"));
    }

    @Disabled
    @ValueSource(strings = {"Streets of Rage 4", "DOOM (2016)"})
    @ParameterizedTest(name = "checking result on site igromania.ru \"{0}\"")
    void commonSearchTest(String testData) {
        $(".search_box_link").click();
        $(".ssearch_inp").setValue(testData);
        $(withText(testData)).click();
        $(".name-layers").shouldHave(text(testData));
    }

    @CsvSource(value = {
            "Streets of Rage 4| PC",
            "DOOM (2016)| PC"
    }, delimiter = '|')
    @ParameterizedTest(name = "проверка сайта игромания")
    void complexSearchTest(String testData, String testData2) {
        $(".search_box_link").click();
        $(".ssearch_inp").setValue(testData);
        $(withText(testData)).click();
        $(".name-layers").shouldHave(text(testData));
        $(".game-tags").shouldHave(text(testData2));
    }

    static Stream<Arguments> mixedArgumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("Streets of Rage 4", "PC", "Рецензии ", "на "),
                Arguments.of("DOOM (2016)", "PC", "Рецензии ", "на "),
                Arguments.of("Quake 4", "PC", "Рецензии ", "на ")
        );
    }

    @MethodSource(value = "mixedArgumentsTestDataProvider")
    @ParameterizedTest(name = "name{2}")
    void mixedArgumentTest(String testData1, String testData2, String testData3, String testData4) {
        $(".search_box_link").click();
        $(".ssearch_inp").setValue(testData1);
        $(withText(testData1)).click();
        $(".name-layers").shouldHave(text(testData1));
        $(".game-tags").shouldHave(text(testData2));
        $(byText(testData3 + testData4 + testData1)).click();
        $(".name-layers").shouldHave(text(testData3 + testData1));
    }

    @Test
    void testBranch1() {
        assertTrue(2 < 3);
    }

    @Test
    void testBranch2() {
        assertTrue(2 < 3);
    }

    @Test
    void testBranch3() {
        assertTrue(2 < 3);
    }


}
