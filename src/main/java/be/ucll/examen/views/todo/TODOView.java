package be.ucll.examen.views.todo;

import be.ucll.examen.entity.Todo;
import be.ucll.examen.entity.TodoFor;
import be.ucll.examen.entity.TodoStatus;
import be.ucll.examen.security.AuthenticatedUser;
import be.ucll.examen.services.TodoService;
import be.ucll.examen.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.PermitAll;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import com.vaadin.flow.router.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@PageTitle("TODO")
@Route(value = "todo", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class TODOView extends Composite<VerticalLayout>{

    @Autowired
    private AuthenticatedUser authenticatedUser;

    private Long todoId;
    @Autowired
    private TodoService todoService;
    private TextField textField;
    private TextArea textArea;
    private DatePicker creationDatePicker;
    private DatePicker dueDatePicker;
    private Select<TodoStatus> statusSelect;
    private Select<TodoFor> todoForSelect;
    private Grid<Todo> multiSelectGrid;

    public TODOView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        Select<TodoFor> filterTodoForSelect = new Select<>();
        Select<TodoStatus> filterStatusSelect = new Select<>();
        DatePicker fromDatePicker = new DatePicker();
        DatePicker tillDatePicker = new DatePicker();
        Button buttonPrimary = new Button("Filter");
        Button buttonSecondary = new Button("new todo");
        Hr hr = new Hr();

        multiSelectGrid = new Grid<>(Todo.class);
        //<theme-editor-local-classname>
        multiSelectGrid.addClassName("t-odo-view-grid-1");
        VerticalLayout layoutColumn3 = new VerticalLayout();
        textField = new TextField("Name");
        textArea = new TextArea("Comment:");
        creationDatePicker = new DatePicker("Creation date:");
        dueDatePicker = new DatePicker("Due date:");
        statusSelect = new Select<>();
        todoForSelect = new Select<>();
        Button buttonPrimary2 = new Button("Create");

        getContent().setSpacing(false);
        getContent().setPadding(false);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);

        layoutRow.addClassName(Gap.XSMALL);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("100%");
        layoutColumn2.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layoutColumn2.setAlignItems(FlexComponent.Alignment.START);

        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.XLARGE);
        layoutRow2.addClassName(Padding.XSMALL);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        layoutRow2.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.setHeight("100%");
        layoutRow2.addClassName(Gap.XLARGE);
        layoutRow2.addClassName(Padding.XSMALL);
        layoutRow3.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow3.setJustifyContentMode(FlexComponent.JustifyContentMode.END);


        filterTodoForSelect.setLabel("For:");
        filterTodoForSelect.setItems(TodoFor.values());
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, filterTodoForSelect);
        filterTodoForSelect.getStyle().set("flex-grow", "1");
        filterTodoForSelect.setMaxWidth("200px");

        filterStatusSelect.setLabel("Status:");
        filterStatusSelect.setItems(TodoStatus.values());
        filterStatusSelect.getStyle().set("flex-grow", "1");
        filterStatusSelect.setMaxWidth("200px");

        fromDatePicker.setLabel("From:");
        fromDatePicker.getStyle().set("flex-grow", "1");
        fromDatePicker.setMaxWidth("200px");

        tillDatePicker.setLabel("Till:");
        tillDatePicker.getStyle().set("flex-grow", "1");
        tillDatePicker.setMaxWidth("200px");

        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(e -> filterGrid(multiSelectGrid, filterTodoForSelect.getValue(), filterStatusSelect.getValue(), fromDatePicker.getValue(), tillDatePicker.getValue()));
        buttonSecondary.setWidth("min-content");
        buttonSecondary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.addClickListener(e -> {layoutColumn3.setVisible(!layoutColumn3.isVisible());});


        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("100%");
        layoutRow2.getStyle().set("flex-grow", "1");

        multiSelectGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, multiSelectGrid);
        //layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, multiSelectGrid);
        multiSelectGrid.setWidth("100%");
        multiSelectGrid.setMinHeight("600px");
        multiSelectGrid.setHeight("100%");
        multiSelectGrid.getStyle().set("flex-grow", "2");
        setGrid(multiSelectGrid);

        layoutColumn3.addClassName(Padding.XLARGE);
        layoutColumn3.setHeight("min-content");
        layoutColumn3.setWidth("min-content");
        layoutColumn3.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layoutColumn3.setAlignItems(FlexComponent.Alignment.END);
        layoutColumn3.setFlexGrow(1.0, layoutRow3);

        statusSelect.setLabel("Status");
        statusSelect.setItems(TodoStatus.values());

        todoForSelect.setLabel("This item is for:");
        todoForSelect.setItems(TodoFor.values());


        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(e -> createTodo());

        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(filterTodoForSelect);
        layoutRow2.add(filterStatusSelect);
        layoutRow2.add(fromDatePicker);
        layoutRow2.add(tillDatePicker);
        layoutRow2.add(buttonPrimary);
        layoutRow2.add(buttonSecondary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(multiSelectGrid);
        layoutRow3.add(layoutColumn3);
        layoutColumn3.add(textField);
        layoutColumn3.add(textArea);
        layoutColumn3.add(creationDatePicker);
        layoutColumn3.add(dueDatePicker);
        layoutColumn3.add(statusSelect);
        layoutColumn3.add(todoForSelect);
        layoutColumn3.add(buttonPrimary2);

        layoutColumn3.setVisible(false);
    }

    private void setGrid(Grid<Todo> grid) {
        grid.setItems(query -> todoService.list(PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    private void filterGrid(Grid<Todo> grid, TodoFor todoFor, TodoStatus status, LocalDate fromDate, LocalDate tillDate) {
        grid.setItems(query -> todoService.findByFilter(todoFor, status, fromDate, tillDate, PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
    }

    private void createTodo() {
        Todo newTodo = new Todo();
        newTodo.setTitle(textField.getValue());
        newTodo.setComment(textArea.getValue());
        newTodo.setCreationDate(creationDatePicker.getValue());
        newTodo.setDueDate(dueDatePicker.getValue());
        newTodo.setStatus(statusSelect.getValue());
        newTodo.setTodoFor(todoForSelect.getValue());

        todoService.save(newTodo);
        textField.clear();
        textArea.clear();
        creationDatePicker.clear();
        dueDatePicker.clear();
        statusSelect.clear();
        todoForSelect.clear();
        // Refresh grid
        setGrid(multiSelectGrid);
    }
}
