package com.example.application.ui.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class Scene extends VerticalLayout {

    public Scene() {
        setWidth("40%");
        setHeight("60%");
        addClassNames(LumoUtility.BorderRadius.LARGE,
                LumoUtility.BorderColor.SUCCESS_50,
                LumoUtility.Border.ALL,
                LumoUtility.Background.CONTRAST_20);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }

}
