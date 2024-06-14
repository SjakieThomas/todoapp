package be.ucll.examen.views.todo;

import be.ucll.examen.data.SamplePerson;
import be.ucll.examen.services.SamplePersonService;
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
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("TODO")
@Route(value = "todo", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class TODOView extends Composite<VerticalLayout> {

    public TODOView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        Select select = new Select();
        Select select2 = new Select();
        DatePicker datePicker = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        Button buttonPrimary = new Button();
        Hr hr = new Hr();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        Grid multiSelectGrid = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn4 = new VerticalLayout();
        TextField textField = new TextField();
        TextArea textArea = new TextArea();
        DatePicker datePicker3 = new DatePicker();
        Select select3 = new Select();
        Select select4 = new Select();
        Button buttonPrimary2 = new Button();
        getContent().setSpacing(false);
        getContent().setPadding(false);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        layoutRow.addClassName(Gap.XSMALL);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.END);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("100%");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.START);
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.XLARGE);
        layoutRow2.addClassName(Padding.XSMALL);
        layoutRow2.setWidth("100%");
        layoutRow2.setHeight("min-content");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);
        select.setLabel("For:");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.START, select);
        select.getStyle().set("flex-grow", "1");
        select.setMaxWidth("200px");
        setSelectSampleData(select);
        select2.setLabel("Status:");
        select2.getStyle().set("flex-grow", "1");
        select2.setMaxWidth("200px");
        setSelectSampleData(select2);
        datePicker.setLabel("From:");
        datePicker.getStyle().set("flex-grow", "1");
        datePicker.setMaxWidth("200px");
        datePicker2.setLabel("Till:");
        datePicker2.getStyle().set("flex-grow", "1");
        datePicker2.setMaxWidth("200px");
        buttonPrimary.setText("Filter");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.END, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("100%");
        layoutColumn3.getStyle().set("flex-grow", "1");
        multiSelectGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, multiSelectGrid);
        multiSelectGrid.setWidth("100%");
        multiSelectGrid.setHeight("100%");
        setGridSampleData(multiSelectGrid);
        layoutColumn4.addClassName(Padding.XLARGE);
        layoutColumn4.setHeight("min-content");
        layoutColumn4.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn4.setAlignItems(Alignment.END);
        textField.setLabel("Text field");
        textField.setWidth("min-content");
        textArea.setLabel("Comment:");
        textArea.setWidth("100%");
        datePicker3.setLabel("Due date:");
        datePicker3.setWidth("min-content");
        select3.setLabel("Status");
        select3.setWidth("min-content");
        setSelectSampleData(select3);
        select4.setLabel("this item is for:");
        select4.setWidth("min-content");
        setSelectSampleData(select4);
        buttonPrimary2.setText("Create");
        layoutColumn4.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary2);
        buttonPrimary2.setWidth("100%");
        buttonPrimary2.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(select);
        layoutRow2.add(select2);
        layoutRow2.add(datePicker);
        layoutRow2.add(datePicker2);
        layoutRow2.add(buttonPrimary);
        layoutColumn2.add(hr);
        layoutColumn2.add(layoutColumn3);
        layoutColumn3.add(multiSelectGrid);
        layoutRow.add(layoutColumn4);
        layoutColumn4.add(textField);
        layoutColumn4.add(textArea);
        layoutColumn4.add(datePicker3);
        layoutColumn4.add(select3);
        layoutColumn4.add(select4);
        layoutColumn4.add(buttonPrimary2);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setSelectSampleData(Select select) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        select.setItems(sampleItems);
        select.setItemLabelGenerator(item -> ((SampleItem) item).label());
        select.setItemEnabledProvider(item -> !Boolean.TRUE.equals(((SampleItem) item).disabled()));
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;
}
