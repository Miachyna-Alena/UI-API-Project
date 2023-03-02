package pages;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static aquality.selenium.elements.ElementType.TEXTBOX;

public class ProjectPage extends Form {
    private static final String COLUMN_PATH = "//table[@class = 'table']//tr/td[%s]";

    private ILink lnkTEST(int testId) {
        return getElementFactory().getLink(By.xpath(String.format("//a[@href = 'testInfo?testId=%s']", testId)), "\"Test\" link");
    }

    public ProjectPage(String name) {
        super(By.xpath(String.format("//li[contains(text(), '%s')]", name)), String.format("\"%s Project\" page", name));
    }

    public List<String> getListOfColumnData(int columnIndex) {
        List<ITextBox> column_data = getElementFactory().findElements(By.xpath(String.format(COLUMN_PATH, columnIndex)), TEXTBOX);
        List<String> column_data_text = new ArrayList<>();
        column_data.forEach(element -> column_data_text.add(element.getText()));
        return column_data_text;
    }

    public boolean isTestDisplay(int testId) {
        return lnkTEST(testId).state().waitForDisplayed();
    }
}
