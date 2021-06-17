package com.spring.lgm0k7.core.component;

import com.spring.lgm0k7.security.config.SecurityUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MenuComponent extends HorizontalLayout {

    public MenuComponent(){
        Anchor factoryLink = new Anchor();
        factoryLink.setHref("/factorymanager");
        factoryLink.setText("Factories");

        Anchor carLink = new Anchor();
        carLink.setHref("/carmanager");
        carLink.setText("Cars");
        add(factoryLink, carLink);

        if (SecurityUtils.isAdmin()) {
            Anchor userLink = new Anchor();
            userLink.setHref("/usermanager");
            userLink.setText("Users");
            add(userLink);
        }
    }
}
