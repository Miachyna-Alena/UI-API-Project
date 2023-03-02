package pages.forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddProjectForm extends Form {
    private final ITextBox txbPROJECT_NAME = getElementFactory().getTextBox(By.xpath("//input[@id = 'projectName']"), "\"Enter Project Name\" text box");
    private final IButton btnSAVE_PROJECT = getElementFactory().getButton(By.xpath("//button[@type = 'submit']"), "\"Save Project\" button");
    private final ILabel lblSUCCESS_ALERT = getElementFactory().getLabel(By.xpath("//div[contains(@class,'alert-success')]"), "\"Success Alert\".");

    public AddProjectForm() {
        super(By.xpath("//form[@id = 'addProjectForm']"), "\"Add Project\" form");
    }

    public void inputProjectName(String name) {
        txbPROJECT_NAME.type(name);
    }

    public void clickSaveProjectButton() {
        btnSAVE_PROJECT.clickAndWait();
    }

    public boolean isSuccessAlertDisplayed() {
        return lblSUCCESS_ALERT.state().isDisplayed();
    }
}