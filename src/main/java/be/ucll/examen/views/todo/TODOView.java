package be.ucll.examen.views.todo;

import be.ucll.examen.entity.Todo;
import be.ucll.examen.entity.TodoFor;
import be.ucll.examen.entity.TodoStatus;
import be.ucll.examen.repository.TodoRepository;
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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@PageTitle("TODO")
@Route(value = "todo", layout = MainLayout.class)
@Uses(Icon.class)
@PermitAll
public class TODOView extends Composite<VerticalLayout>{

    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;


    private final Select<TodoFor> todoForSelect = new Select<>("Todo for",null, TodoFor.values());
    private final Select<TodoStatus> statusSelect = new Select<>("Todo status",null, TodoStatus.values());
    private final TextField textField = new TextField("Name");
    private final TextArea textArea = new TextArea("Comment:");
    private final DatePicker dueDatePicker = new DatePicker("Due date:");
    private final DatePicker creationDatePicker = new DatePicker("Creation date:");
    private final Grid<Todo> multiSelectGrid = new Grid<>(Todo.class, false);

    public TODOView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        HorizontalLayout layoutRow = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        HorizontalLayout layoutRow3 = new HorizontalLayout();

        Hr hr = new Hr();

        Button buttonPrimary = new Button("Filter");
        Button buttonPrimary2 = new Button("Create");
        Button buttonSecondary = new Button("new todo");
        Select<TodoFor> filterTodoForSelect = new Select<>("Todo for",null, TodoFor.values());
        Select<TodoStatus> filterStatusSelect = new Select<>("Todo status",null, TodoStatus.values());
        DatePicker tillDatePicker = new DatePicker("Till:");
        DatePicker fromDatePicker = new DatePicker("From:");

        layoutColumn2.setSizeFull();
        layoutColumn2.expand(layoutRow3, layoutRow2,layoutRow,getContent(),tillDatePicker,fromDatePicker,filterStatusSelect,filterTodoForSelect);
        layoutColumn2.setAlignItems(FlexComponent.Alignment.START);
        layoutColumn2.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        layoutRow.addClassName(Gap.XSMALL);
        layoutRow.setSizeFull();
        layoutRow.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        layoutRow2.setSizeFull();
        layoutRow2.setHeight("min-content");
        layoutRow2.addClassNames(Gap.XLARGE,Padding.XSMALL);
        layoutRow2.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, multiSelectGrid);
        layoutRow2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        layoutRow3.setSizeFull();
        layoutRow3.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutRow3.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        filterTodoForSelect.setMaxWidth("200px");
        filterStatusSelect.setMaxWidth("200px");
        fromDatePicker.setMaxWidth("200px");
        tillDatePicker.setMaxWidth("200px");

        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClickListener(e -> filterGrid(multiSelectGrid, filterTodoForSelect.getValue(), filterStatusSelect.getValue(), fromDatePicker.getValue(), tillDatePicker.getValue()));

        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary2.addClickListener(e -> createTodo());

        buttonSecondary.setWidth("min-content");
        buttonSecondary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.addClickListener(e -> {layoutColumn3.setVisible(!layoutColumn3.isVisible());});


        multiSelectGrid.setSizeFull();
        multiSelectGrid.setMinHeight("600px");
        multiSelectGrid.getStyle().set("flex-grow", "2");
        multiSelectGrid.addClassName("t-odo-view-grid-1");
        multiSelectGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        multiSelectGrid.addColumns("title","comment","todoFor","status","creationDate","dueDate");
        multiSelectGrid.addComponentColumn(report -> createStatusBadge(report.getStatus().toString())).setHeader("Status");
        multiSelectGrid.addColumn(new NativeButtonRenderer<>("Remove item", clickedItem -> {
            todoService.delete(clickedItem);
            setGrid(multiSelectGrid);
        }));

//        multiSelectGrid.setPartNameGenerator(person -> {
//            LocalDate today = LocalDate.now();
//            LocalDate eventDate = person.getDueDate();
//            long daysUntilEvent = ChronoUnit.DAYS.between(today, eventDate);
//            if (daysUntilEvent <= 3) {
//                return "low-rating";
//            }
//            if (daysUntilEvent <= 7) {
//                return "high-rating";
//            }
//            return null;
//        });
//        multiSelectGrid.setPartNameGenerator(todo -> {
//            LocalDate today = LocalDate.now();
//            LocalDate eventDate = todo.getDueDate();
//            long daysUntilEvent = ChronoUnit.DAYS.between(today, eventDate);
//            int days= (int) daysUntilEvent;
//            if (days >= 8)
//                return "date-near";
//            if (days <= 4)
//                return "date-soon";
//            return null;
//        });

        //<theme-editor-local-classname>

        getContent().setSpacing(false);
        getContent().setPadding(false);
        getContent().setWidth("100%");
        getContent().setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        getContent().setAlignItems(FlexComponent.Alignment.CENTER);

        layoutColumn3.addClassName(Padding.XLARGE);
        layoutColumn3.setSizeFull();
        layoutColumn3.setWidth("min-content");
        layoutColumn3.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layoutColumn3.setAlignItems(FlexComponent.Alignment.END);
        layoutColumn3.setFlexGrow(1.0, layoutRow3);
        //layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, multiSelectGrid);

        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow2,hr,layoutRow3);
        layoutRow2.add(filterTodoForSelect,filterStatusSelect,fromDatePicker,tillDatePicker,buttonPrimary,buttonSecondary);
        layoutRow3.add(multiSelectGrid,layoutColumn3);
        layoutColumn3.add(textField,textArea,creationDatePicker,dueDatePicker,statusSelect,todoForSelect,buttonPrimary2);

        setGrid(multiSelectGrid);
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
    private Span createStatusBadge(String status) {
        String theme = switch (status) {
            case "DONE" -> "badge contrast  primary";
            case "TODO" -> "badge error primary";
            default -> "badge success  primary";
        };
        Span badge = new Span(status);
        badge.getElement().getThemeList().add(theme);
        return badge;
    }


}
