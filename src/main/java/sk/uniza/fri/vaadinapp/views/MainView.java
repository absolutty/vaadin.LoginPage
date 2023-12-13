package sk.uniza.fri.vaadinapp.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import sk.uniza.fri.vaadinapp.services.SecurityService;

@Route("")
@PageTitle("Main | Vaadin CRM")
@PermitAll
public class MainView extends VerticalLayout {

    public MainView(@Autowired SecurityService securityService) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON, MenuBarVariant.LUMO_PRIMARY);
        menuBar.addItem("Používateľ");

        MenuItem item = menuBar.addItem(new Icon(VaadinIcon.CHEVRON_DOWN));
        SubMenu subItems = item.getSubMenu();

        RouterLink editUserLink = new RouterLink("Uprav používateľa", UserView.class);
        subItems.addItem(editUserLink);

        subItems.addItem("Odhlás sa").addClickListener(clickEvent -> {
            securityService.logout();
        });

        add(menuBar);
    }

}
