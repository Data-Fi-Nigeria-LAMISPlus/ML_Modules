package org.lamisplus.modules.ml.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder
public class ModelConfigs{
    @NonNull
    private final String facilityId;
    private final String debug;
    @NonNull
    private final String modelId;
    @NonNull
    private final String encounterDate;
}
