package com.example.application.ui.pages;

import com.example.application.ui.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="", layout = MainLayout.class)
@PageTitle("Home | CS 553")
public class HomeView extends VerticalLayout {

    public HomeView() {

    }
}
