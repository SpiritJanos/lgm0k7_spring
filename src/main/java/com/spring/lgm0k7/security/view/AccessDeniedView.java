package com.spring.lgm0k7.security.view;

import com.spring.lgm0k7.core.component.MenuComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("403")
public class AccessDeniedView extends VerticalLayout {

    public AccessDeniedView(){
        add(new MenuComponent());
        add("You have no permission to visit this page");
    }
}
