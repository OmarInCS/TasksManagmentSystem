package view.backing;

import java.util.Locale;

import javax.faces.context.FacesContext;

public class TemplateBean {
    public TemplateBean() {
    }

    public String ar_action() {
        // Add event code here...
        Locale loc = new Locale("ar");
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getViewRoot().setLocale(loc);
        
        return null;
    }
    
    public String en_action() {
        // Add event code here...
        Locale loc = new Locale("en");
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getViewRoot().setLocale(loc);
        
        return null;
    }
}
