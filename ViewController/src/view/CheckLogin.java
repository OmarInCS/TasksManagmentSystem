package view;

import java.io.IOException;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class CheckLogin implements PhaseListener {
    
    private String username;
    
    public CheckLogin() {
        super();
    }

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        // TODO Implement this method
        FacesContext ctx = FacesContext.getCurrentInstance();
        Map session = ctx.getExternalContext().getSessionMap();
        username = (String) session.get("user");
        System.out.println("### " + username + " ###"); 
        if(username == null && !ctx.getViewRoot().getViewId().contains("Home")){
            try 
            {
                String root = ctx.getExternalContext().getRequestContextPath();
                ctx.getExternalContext().redirect(root + "/faces/Home.jspx");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        // TODO Implement this method
    }

    @Override
    public PhaseId getPhaseId() {
        // TODO Implement this method
        return PhaseId.RESTORE_VIEW;
    }
}
