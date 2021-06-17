package com.spring.lgm0k7.factory.view;

import com.spring.lgm0k7.core.component.MenuComponent;
import com.spring.lgm0k7.factory.entity.FactoryEntity;
import com.spring.lgm0k7.factory.service.FactoryService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


@Route
public class FactoryManagerView extends VerticalLayout {

    private FactoryEntity selectedFactory;
    private VerticalLayout form;
    private TextField name;
    private Binder<FactoryEntity> binder;

    @Autowired
    private FactoryService service;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        Grid<FactoryEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(FactoryEntity::getId).setHeader("ID");
        grid.addColumn(FactoryEntity::getName).setHeader("Factory");
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<FactoryEntity> grid){
        form = new VerticalLayout();
        binder = new Binder<>(FactoryEntity.class);
        name = new TextField();
        form.add(new Text("Factory"), name);

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.addClickListener(buttonClickEvent -> {
            if (selectedFactory.getId() != null){
                service.update(selectedFactory);
            } else {
                service.create(selectedFactory);
            }
            grid.setItems(service.findAll());
            form.setVisible(false);
        });
        form.add(saveButton);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }

    private void addButtonBar(Grid<FactoryEntity> grid){
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteButton = new Button();
        deleteButton.setEnabled(false);
        deleteButton.setText("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedFactory.getId());
            grid.setItems(service.findAll());
            selectedFactory = null;
            deleteButton.setEnabled(false);
            form.setVisible(false);
            Notification.show("Succesfully deleted");
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedFactory = event.getValue();
            deleteButton.setEnabled(selectedFactory != null);
            form.setVisible(selectedFactory != null);
            binder.setBean(selectedFactory);
        });

        Button addButton = new Button();
        addButton.setText("Add");
        addButton.addClickListener(buttonClickEvent -> {
            selectedFactory = new FactoryEntity();
            binder.setBean(selectedFactory);
            form.setVisible(true);
        });
        addButton.setIcon(VaadinIcon.PLAY.create());
        buttonBar.add(deleteButton, addButton);
        add(buttonBar);
    }
}
