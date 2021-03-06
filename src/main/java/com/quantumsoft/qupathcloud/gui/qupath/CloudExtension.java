// Copyright (C) 2019 Google LLC
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.quantumsoft.qupathcloud.gui.qupath;

import com.quantumsoft.qupathcloud.gui.windows.CloudWindow;
import com.quantumsoft.qupathcloud.repository.Repository;
import com.quantumsoft.qupathcloud.synchronization.SynchronizationProjectWithDicomStore;
import javafx.scene.control.Button;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.extensions.QuPathExtension;

public class CloudExtension implements QuPathExtension {
    private static final String EXTENSION_NAME = "Cloud extension";
    private static final String EXTENSION_DESCRIPTION = "Adds integration with Google Cloud Healthcare API";

    public void installExtension(QuPathGUI qupath) {
        Button cloudButton = new Button("Cloud");
        Button synchronizeButton = new Button("Synchronize");

        cloudButton.setOnAction(e -> {
            CloudWindow window = new CloudWindow(qupath);
            window.showCloudWindow();
        });
        cloudButton.disableProperty().bind(qupath.projectProperty().isNull());

        synchronizeButton.setOnAction(event -> {
            SynchronizationProjectWithDicomStore sync = new SynchronizationProjectWithDicomStore(qupath,
                    Repository.INSTANCE.getDicomStore());
            sync.synchronization();
        });
        synchronizeButton.disableProperty().bind(Repository.INSTANCE.getDicomStoreProperty().isNull());

        qupath.addToolbarSeparator();
        qupath.addToolbarButton(cloudButton);
        qupath.addToolbarButton(synchronizeButton);
    }

    public String getName() {
        return EXTENSION_NAME;
    }

    public String getDescription() {
        return EXTENSION_DESCRIPTION;
    }
}
