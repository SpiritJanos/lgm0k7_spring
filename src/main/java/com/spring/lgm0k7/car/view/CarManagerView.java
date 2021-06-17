package com.spring.lgm0k7.car.view;

import com.spring.lgm0k7.car.entity.CarEntity;
import com.spring.lgm0k7.car.service.CarService;
import com.spring.lgm0k7.core.component.MenuComponent;
import com.spring.lgm0k7.factory.entity.FactoryEntity;
import com.spring.lgm0k7.factory.service.FactoryService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class CarManagerView extends VerticalLayout {

    private CarEntity selectedFactory;
    private VerticalLayout form;
    private TextField type;
    private ComboBox<FactoryEntity> factory;
    private TextField door;
    private TextField year;
    private Binder<CarEntity> binder;

    @Autowired
    private CarService service;
    @Autowired
    private FactoryService factoryService;

    @PostConstruct
    public void init(){
        add(new MenuComponent());
        Grid<CarEntity> grid = new Grid<>();
        grid.setItems(service.findAll());
        grid.addColumn(CarEntity::getId).setHeader("ID");
        grid.addColumn(CarEntity::getType).setHeader("Type");
        grid.addColumn(CarEntity::getDoor).setHeader("Doors");
        grid.addColumn(CarEntity::getYear).setHeader("Year");
        grid.addColumn(carEntity -> {
            if (carEntity.getFactory() != null) {
                return carEntity.getFactory().getName();
            }
            return "";
        }).setHeader("Factory");
        addButtonBar(grid);
        add(grid);
        addForm(grid);
    }

    private void addForm(Grid<CarEntity> grid){
        form = new VerticalLayout();
        binder = new Binder<>(CarEntity.class);
        type = new TextField();
        form.add(new Text("Type"), type);
        factory = new ComboBox<>();
        factory.setItems(factoryService.findAll());
        factory.setItemLabelGenerator(factoryEntity -> factoryEntity.getName());
        form.add(new Text("Factory"), factory);
        door = new TextField();
        form.add(new Text("Doors"), door);
        year = new TextField();
        form.add(new Text("Year"), year);

        Button saveButton = new Button();
        saveButton.setText("Save");
        saveButton.addClickListener(buttonClickEvent -> {
            if (selectedFactory.getId() != null) {
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
        binder.forField(door).withNullRepresentation("").withConverter(new StringToIntegerConverter(Integer.valueOf(0), "integers only"))
                .bind(CarEntity::getDoor, CarEntity::setDoor);
        binder.forField(year).withNullRepresentation("").withConverter(new StringToIntegerConverter(Integer.valueOf(0), "integers only"))
                .bind(CarEntity::getYear, CarEntity::setYear);
        binder.bindInstanceFields(this);
    }

    private void addButtonBar(Grid<CarEntity> grid){
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
            Notification.show("Successfully deleted");
        });
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedFactory = event.getValue();
            deleteButton.setEnabled(selectedFactory != null);
            form.setVisible(selectedFactory != null);
            binder.setBean(selectedFactory);
        });

        Button addButton = new Button();
        addButton.addClickListener(buttonClickEvent -> {
            selectedFactory = new CarEntity();
            binder.setBean(selectedFactory);
            form.setVisible(true);
        });
        addButton.setIcon(VaadinIcon.PLUS.create());
        buttonBar.add(deleteButton, addButton);
        add(buttonBar);
    }
}
