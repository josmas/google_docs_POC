package org.jdesktop.wonderland.modules.googledocspoc.client;

import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jdesktop.wonderland.client.hud.CompassLayout.Layout;
import org.jdesktop.wonderland.client.hud.HUD;
import org.jdesktop.wonderland.client.hud.HUDComponent;
import org.jdesktop.wonderland.client.hud.HUDManagerFactory;
/**
 *
 * @author jos
 */
public class GoogleDocsPOCHUD {

    private static final Logger logger = Logger.getLogger(GoogleDocsPOCHUD.class.getName());
    private HUD mainHUD;
    private HUDComponent googleDocsPOCHUD;

    /**
     * Constructor to grab the main HUD area, and display the HUD within it.
     */
    public GoogleDocsPOCHUD() {
        mainHUD = HUDManagerFactory.getHUDManager().getHUD("main");
        displayHud();
    }


    private void displayHud() {
        createHUDComponent();
        setHudComponentVisible(true);
    }

    /**
     * Creates the HUD Component, if it does not exist yet, and adds it to the
     * CENTER of the main HUD area (entire screen above the 3D scene).
     */
    private void createHUDComponent() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (googleDocsPOCHUD == null) {
                    JPanel panelForHUD = new googleDocsPOCPanel();
                    googleDocsPOCHUD = mainHUD.createComponent(panelForHUD);
                    googleDocsPOCHUD.setDecoratable(true);
                    googleDocsPOCHUD.setName("Sample HUD");
                    googleDocsPOCHUD.setPreferredLocation(Layout.CENTER);
                    mainHUD.addComponent(googleDocsPOCHUD);
                }
            }
        });

    }

    /**
     * Changes the visibility of the HUD according to the boolean passed.
     * @param show
     */
    public void setHudComponentVisible(final boolean show) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                googleDocsPOCHUD.setVisible(show);
            }
        });

    }
}
