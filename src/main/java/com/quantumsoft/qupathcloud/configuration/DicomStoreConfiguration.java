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

package com.quantumsoft.qupathcloud.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantumsoft.qupathcloud.entities.DicomStore;
import com.quantumsoft.qupathcloud.exception.QuPathCloudException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class DicomStoreConfiguration {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String configurationFileName = "conf.json";
    private File configurationFileInProjectDirectory;
    private ObjectMapper mapper;

    public DicomStoreConfiguration(File projectDirectory){
        configurationFileInProjectDirectory = new File(projectDirectory, configurationFileName);
        mapper = new ObjectMapper();
    }

    public DicomStore readConfiguration() throws QuPathCloudException {
        if (configurationFileInProjectDirectory.exists()){
            try {
                LOGGER.debug("Start reading configuration");
                return mapper.readValue(configurationFileInProjectDirectory, DicomStore.class);
            } catch (IOException e) {
                throw new QuPathCloudException(e);
            }
        }
        return null;
    }

    public void saveConfiguration(DicomStore selectedDicomStore) throws QuPathCloudException {
        try {
            LOGGER.debug("Start writing configuration");
            mapper.writeValue(configurationFileInProjectDirectory, selectedDicomStore);
        } catch (IOException e) {
            throw new QuPathCloudException(e);
        }
    }
}
