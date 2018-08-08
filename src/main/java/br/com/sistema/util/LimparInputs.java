package br.com.sistema.util;

import java.util.List;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.selectcheckboxmenu.SelectCheckboxMenu;

/**
 *
 * @author ericsson
 */
public class LimparInputs implements ActionListener {

    @Override
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = context.getViewRoot();
        List<UIComponent> children = viewRoot.getChildren();

        resetInputValues(children);
    }

    private void resetInputValues(List<UIComponent> children) {
        for (UIComponent component : children) {
            if (component instanceof SelectCheckboxMenu) {
                EditableValueHolder input = (EditableValueHolder) component;
                input.resetValue();
            }
            
            if (component.getChildCount() > 0) {
                resetInputValues(component.getChildren());
            } else {
                if (component instanceof EditableValueHolder) {
                    EditableValueHolder input = (EditableValueHolder) component;
                    input.resetValue();
                }
            }

        }
    }
}
