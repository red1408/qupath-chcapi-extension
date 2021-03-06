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

package com.quantumsoft.qupathcloud.converter.qpdata;

import com.quantumsoft.qupathcloud.exception.QuPathCloudException;
import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Fragments;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DcmToDataConverter {
    private File inputFile;
    private File outputDirectory;

    public DcmToDataConverter(File inputFile, File outputDirectory) {
        this.inputFile = inputFile;
        this.outputDirectory = outputDirectory;
    }

    public File convertDcmToQuPathData() throws QuPathCloudException {
        try (DicomInputStream dis = new DicomInputStream(inputFile)) {
            Attributes attrs = dis.readDataset(-1, -1);
            byte[] qpdataBytes = (byte[]) ((Fragments) attrs.getValue(DataToDcmConverter.QPDATA_TAG)).get(0);
            String qpdataName = attrs.getString(Tag.SOPAuthorizationComment);
            File outputFile = new File(outputDirectory, qpdataName + ".qpdata");
            try (FileOutputStream fos = new FileOutputStream(outputFile, false)) {
                fos.write(qpdataBytes, 0, qpdataBytes.length);
                fos.close();
                return outputFile;
            }
        } catch (IOException e) {
            throw new QuPathCloudException(e);
        }
    }
}
