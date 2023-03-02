package pages;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.util.List;

import static aquality.selenium.elements.ElementType.LINK;
import static org.openqa.selenium.By.cssSelector;

public class ProjectsPage extends Form {
    private static final String PROJECT_LINK_PATH = "//div[@class='list-group']/a[contains(text(),'%s')]";
    private static final By PROJECTS_CSS = cssSelector(".list-group a");
    private final ILabel lblFOOTER = getElementFactory().getLabel(By.xpath("//p[contains(@class, 'footer-text')]//span[text()]"), "\"Footer text\"");
    private final IButton btnADD_PROJECT = getElementFactory().getButton(By.xpath("//a[contains(@href, 'addProject')]"), "\"+Add\" button");

    public ProjectsPage() {
        super(By.xpath("//div[contains(@class, 'list-group')]"), "\"Projects\" page");
    }

    public int getVersionNumberFromFooter() {
        String footerText = lblFOOTER.getText();
        return Integer.parseInt(String.valueOf(footerText.charAt(footerText.length() - 1)));
    }

    public ILink getProjectLink(String name) {
        return getElementFactory().getLink(By.xpath(String.format(PROJECT_LINK_PATH, name)), String.format("\"%s Project\" link", name));
    }

    public void clickProjectLink(String name) {
        getProjectLink(name).clickAndWait();
    }

    public void clickAddProjectButton() {
        btnADD_PROJECT.clickAndWait();
    }

    public int findProject(String name) {
        List<ILink> projects = getElementFactory().findElements(PROJECTS_CSS, "Projects", LINK);
        List<ILink> names = projects
                .stream()
                .filter(link -> link.getText().equals(name)).toList();
        return names.size();
    }

    public boolean isNewProjectInProjectsList(String name) {
        return findProject(name) > 0;
    }
}
