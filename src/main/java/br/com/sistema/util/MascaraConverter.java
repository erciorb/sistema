package br.com.sistema.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ericsson
 */
@FacesConverter("mascaraConverter")
public class MascaraConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String valor) {
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object valor) {
        String saida = "";
        if (valor == "" || valor == null) {
            return saida = "";
        }
        if (valor.toString().length() == 10) {
            saida = valor.toString();
        } else {
            try {
                SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

                Date data = formato.parse(valor.toString());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                saida = formatter.format(data);
            } catch (ParseException ex) {
                Logger.getLogger(MascaraConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return saida;
    }
}
