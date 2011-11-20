package org.jdesktop.wonderland.modules.googledocspoc.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import org.jdesktop.wonderland.client.BaseClientPlugin;
import org.jdesktop.wonderland.client.jme.JmeClientMain;
import org.jdesktop.wonderland.client.login.ServerSessionManager;
import org.jdesktop.wonderland.common.annotation.Plugin;

/**
 * Plugin to make the HUD available in the 'window' menu
 * @author jos
 */
@Plugin
public class GoogleDocsPOCHUDPlugin extends BaseClientPlugin {

    private JMenuItem googleDocsPOCHUDMI = null;
    private GoogleDocsPOCHUD googleDocsPOCHUD;
    private boolean googleDocsPOCHUDEnabled = false;

    /**
     * Creates a new Menu Item for the HUD that will allow to show/hide it.
     * @param loginInfo
     */
    @Override
    public void initialize(ServerSessionManager loginInfo) {
        googleDocsPOCHUDMI = new JCheckBoxMenuItem("Google Docs HUD");
        googleDocsPOCHUDMI.setSelected(false);
        googleDocsPOCHUDMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                googleDocsPOCHUDEnabled = !googleDocsPOCHUDEnabled;
                googleDocsPOCHUDMI.setSelected(googleDocsPOCHUDEnabled);
                if (googleDocsPOCHUD == null ) {
                    googleDocsPOCHUD = new GoogleDocsPOCHUD();
                }
                else
                    googleDocsPOCHUD.setHudComponentVisible(googleDocsPOCHUDEnabled);
            }
        });

        super.initialize(loginInfo);
    }

    /**
     * Adds the Menu Item created in initialize to the Window Menu in the
     * Wonderland Client
     */
    @Override
    public void activate() {
        JmeClientMain.getFrame().addToWindowMenu(googleDocsPOCHUDMI);
    }

     /**
     * Removes the Menu Item created in initialize to the Window Menu in the
     * Wonderland Client
     */
    @Override
    public void deactivate() {
        JmeClientMain.getFrame().removeFromWindowMenu(googleDocsPOCHUDMI);
    }


}
