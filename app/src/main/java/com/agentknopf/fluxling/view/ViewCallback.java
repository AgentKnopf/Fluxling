package com.agentknopf.fluxling.view;

import com.agentknopf.fluxling.model.Store;

/**
 * Callback that triggers the view to render the data on screen.
 *
 * Created by agentknopf on 28.12.16.
 */
public interface ViewCallback {

    void render(Store store);
}
