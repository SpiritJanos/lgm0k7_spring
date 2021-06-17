package com.spring.lgm0k7.user.view;

import com.spring.lgm0k7.core.component.MenuComponent;
import com.spring.lgm0k7.user.entity.AppRoleEntity;
import com.spring.lgm0k7.user.entity.AppUserEntity;
import com.spring.lgm0k7.user.service.AppRoleService;
import com.spring.lgm0k7.user.service.AppUserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;


@Route
public class UserManagerView extends VerticalLayout {

    private AppUserEntity selectedUser;
    private VerticalLayout form;
    private TextField firstName;
    private TextField lastName;
    private TextField username;
    private CheckboxGroup<AppRoleEntity> authorities;
    private Binder<AppUserEntity> binder;
    //private TextField password;

    @Autowired
    private AppUserService service;
    @Autowired
    private AppRoleService roleService;

    @PostConstruct
    public void init(){
        add(new MenuComponent());
        Grid<AppUserEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(AppUserEntity::getId).setHeader("ID");
        grid.addColumn(AppUserEntity::getFirstName).setHeader("First Name");
        grid.addColumn(AppUserEntity::getLastName).setHeader("Last Name");
        grid.addColumn(AppUserEntity::getUsername).setHeader("Username");
        grid.addColumn(appUserEntity -> {
            StringBuilder builder = new StringBuilder();
            appUserEntity.getAuthorities().forEach(appRoleEntity -> {
                builder.append(appRoleEntity.getAuthority()).append(", ");
            });
            return builder.toString();
        }).setHeader("Role");
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<AppUserEntity> grid){
        form = new VerticalLayout();
        binder = new Binder<>(AppUserEntity.class);
        firstName = new TextField();
        form.add(new Text("First name"), firstName);
        lastName = new TextField();
        form.add(new Text("Last name"), lastName);
        username = new TextField();
        form.add(new Text("Username"), username);
        authorities = new CheckboxGroup<>();
        authorities.setItems(roleService.findAll());
        authorities.setItemLabelGenerator(authorEntity -> authorEntity.getAuthority());
        form.add(new Text("Roles"), authorities);

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.addClickListener(buttonClickEvent -> {
            if (selectedUser.getId() != null) {
                service.update(selectedUser);
            } else {
                selectedUser.setPassword(new BCryptPasswordEncoder().encode("springboot"));
                service.create(selectedUser);
            }
            grid.setItems(service.findAll());
            form.setVisible(false);
        });
        form.add(saveButton);
        add(form);
        form.setVisible(false);
        binder.bindInstanceFields(this);
    }

    private void addButtonBar(Grid<AppUserEntity> grid){
        HorizontalLayout buttonBar = new HorizontalLayout();

        Button deleteButton = new Button();
        deleteButton.setEnabled(false);
        deleteButton.setText("Delete");
        deleteButton.setIcon(VaadinIcon.TRASH.create());
        deleteButton.addClickListener(buttonClickEvent -> {
            service.deleteById(selectedUser.getId());
            grid.setItems(service.findAll());
            selectedUser = null;
            deleteButton.setEnabled(false);
            form.setVisible(false);
            Notification.show("Successfully deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedUser = event.getValue();
            deleteButton.setEnabled(selectedUser != null);
            form.setVisible(selectedUser != null);
            binder.setBean(selectedUser);
        });

        Button addButton = new Button();
        addButton.setText("Add");
        addButton.addClickListener(buttonClickEvent -> {
            selectedUser = new AppUserEntity();
            binder.setBean(selectedUser);
            form.setVisible(true);
            Notification.show("Successfully created a new user! It's password: ");
        });
        addButton.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteButton, addButton);
        add(buttonBar);
    }
}
