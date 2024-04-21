package com.example.application.ui;

import com.example.application.ui.pages.QuizView;
import com.example.application.ui.pages.HomeView;
import com.example.application.ui.pages.ResultsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("CS 553 | Assignment 4");
        logo.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), logo );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.Padding.Vertical.NONE,
                LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);

    }

    private void createDrawer() {
        VerticalLayout navLayout = new VerticalLayout();

        RouterLink home = new RouterLink("Home", HomeView.class);
        navLayout.add(home);
        RouterLink quiz = new RouterLink("Take a Quiz", QuizView.class);
        navLayout.add(quiz);
        RouterLink results = new RouterLink("Quiz Results", ResultsView.class);
        navLayout.add(results);

        addToDrawer(navLayout);
    }


}
