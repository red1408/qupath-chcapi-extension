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

package com.quantumsoft.qupathcloud.dao.spec;

import com.quantumsoft.qupathcloud.exception.QuPathCloudException;

import static com.quantumsoft.qupathcloud.dao.Constants.*;

public class DicomStoresPathBuilder implements PathBuilder{
    private QueryBuilder queryBuilder;

    public DicomStoresPathBuilder(QueryBuilder queryBuilder){
        this.queryBuilder = queryBuilder;
    }

    @Override
    public String toPath() throws QuPathCloudException {
        if(queryBuilder.getLocationId() == null){
            throw new QuPathCloudException("Location must not be null!");
        }
        if(queryBuilder.getDatasetId() == null){
            throw new QuPathCloudException("Dataset must not be null!");
        }
        return STAGE + PROJECTS + queryBuilder.getProjectId() + LOCATIONS + queryBuilder.getLocationId() + DATASETS +
                queryBuilder.getDatasetId() + DICOM_STORES;
    }
}
