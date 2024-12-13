package org.lamisplus.modules.ml.controller.requestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VariableValues {

    @JsonProperty("gender_female")
    int gender_female;
    @JsonProperty("gender_male")
    int gender_male;
    @JsonProperty("marital_status_divorced")
    int marital_status_divorced;
    @JsonProperty("marital_status_married")
    int marital_status_married;
    @JsonProperty("marital_status_other")
    int marital_status_other;
    @JsonProperty("marital_status_single")
    int marital_status_single;
    @JsonProperty("target_group_CHILDREN_OF_KP")
    int target_group_CHILDREN_OF_KP;
    @JsonProperty("target_group_FSW")
    int target_group_FSW;
    @JsonProperty("target_group_GEN_POP")
    int target_group_GEN_POP;
    @JsonProperty("target_group_MSM")
    int target_group_MSM;
    @JsonProperty("target_group_other")
    int target_group_other;
    @JsonProperty("target_group_PWID")
    int target_group_PWID;
    @JsonProperty("target_group_SEXUAL_PARTNER")
    int target_group_SEXUAL_PARTNER;
    @JsonProperty("referred_from_Community_Mobilization")
    int referred_from_Community_Mobilization;
    @JsonProperty("referred_from_OPD")
    int referred_from_OPD;
    @JsonProperty("referred_from_Others")
    int referred_from_Others;
    @JsonProperty("referred_from_Private_Commercial_Health_facility")
    int referred_from_Private_Commercial_Health_facility;
    @JsonProperty("referred_from_Self")
    int referred_from_Self;
    @JsonProperty("testing_settingANC")
    int testing_settingANC;
    @JsonProperty("testing_settingCPMTCT")
    int testing_settingCPMTCT;
    @JsonProperty("testing_settingCT")
    int testing_settingCT;
    @JsonProperty("testing_settingIndex")
    int testing_settingIndex;
    @JsonProperty("testing_settingOVC")
    int testing_settingOVC;
    @JsonProperty("testing_settingOthers")
    int testing_settingOthers;
    @JsonProperty("testing_settingOutreach")
    int testing_settingOutreach;
    @JsonProperty("testing_settingTB")
    int testing_settingTB;
//    @JsonProperty("testing_setting_TB")
//    int testing_setting_TB;
    @JsonProperty("testing_settingWard_Inpatient")
    int testing_settingWard_Inpatient;
    @JsonProperty("age")
    int age;
    @JsonProperty("first_time_visit")
    int first_time_visit;
    @JsonProperty("index_client")
    int index_client;
    @JsonProperty("previously_tested")
    int previously_tested;
    @JsonProperty("clientPregnant")
    int clientPregnant;
    @JsonProperty("stiInLast3Months")
    int stiInLast3Months;
    @JsonProperty("everHadSexualIntercourse")
    int everHadSexualIntercourse;
    @JsonProperty("bloodTransfusionInLast3Months")
    int bloodTransfusionInLast3Months;
    @JsonProperty("moreThan1SexPartnerDuringLast3Months")
    int moreThan1SexPartnerDuringLast3Months;
    @JsonProperty("unprotectedSexWithCasualPartnerInLast3Months")
    int unprotectedSexWithCasualPartnerInLast3Months;
    @JsonProperty("unprotectedSexWithRegularPartnerInLast3Months")
    int unprotectedSexWithRegularPartnerInLast3Months;
    @JsonProperty("sti_symptoms")
    int sti_symptoms;
    @JsonProperty("tb_symptoms")
    int tb_symptoms;
}
