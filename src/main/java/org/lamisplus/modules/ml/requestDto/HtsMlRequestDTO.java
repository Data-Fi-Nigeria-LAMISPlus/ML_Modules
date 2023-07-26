package org.lamisplus.modules.ml.requestDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class HtsMlRequestDTO {
    @NonNull
    private ModelConfigs modelConfigs;
    @NonNull
    private VariableValues variableValues;
}
