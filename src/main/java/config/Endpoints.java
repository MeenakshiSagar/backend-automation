package config;

public class Endpoints {

    //Authentication APIs
    public static final String LOGIN = "/zila/api/login";
    public static final String SUBMIT_OTP = "/zila/api/submit_otp";
    public static final String REFRESH_TOKEN = "/zila/api/refresh_token";

    //Form Structure APIs
    public static final String FORM_STRUCTURE = "/form-structure";

    //Person APIs
    public static final String CREATE_PERSON = "/zila/api/data_entry/create";
    public static final String GET_PERSON = "/zila/api/data_entry/get/{id}";


}
