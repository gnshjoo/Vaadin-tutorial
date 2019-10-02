package com.david.sample.spring;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;



@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {
    private CustomerService service = CustomerService.getInstance();
    private CustomerForm form  = new CustomerForm(this);
    private Grid<Customer> grid = new Grid<>(Customer.class);
    private TextField filterText = new TextField();

    public MainView() {

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        Button addCustomerBtn = new Button("Add new Customer");
        addCustomerBtn.addClickListener(e-> {
            grid.asSingleSelect().clear();
            form.setCustomer(new Customer());
        });

        grid.setColumns("firstName", "lastName", "status");
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCustomerBtn);
        mainContent.setSizeFull();
        grid.setSizeFull();
        add(toolbar, mainContent);
        setSizeFull();
        updateList();
        form.setCustomer(null);

        grid.asSingleSelect().addValueChangeListener(event -> form.setCustomer(grid.asSingleSelect().getValue()));


    }

    public void updateList(){
        grid.setItems(service.findAll(filterText.getValue()));
    }
}
