package com.craft.telegramboot.client;

import com.craft.telegramboot.client.google.GoogleTranslatorRepositoryImpl;
import com.craft.telegramboot.client.google.dto.Data;
import com.craft.telegramboot.client.google.dto.GoogleTranslatorRs;
import com.craft.telegramboot.client.google.dto.TranslatedText;
import com.craft.telegramboot.config.RapidApiConfiguration;
import com.craft.telegramboot.domain.model.Translation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.openMocks;

public class GoogleTranslatorRepositoryImplTest {

    @Mock
    private ObjectMapper mockedObjectMapper;
    @Mock
    private RapidApiConfiguration mockedRapidApiConfiguration;
    @Mock
    private OkHttpClient mockedOkHttpClient;

    private GoogleTranslatorRepositoryImpl underTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        underTest = new GoogleTranslatorRepositoryImpl(mockedObjectMapper, mockedRapidApiConfiguration, mockedOkHttpClient);

        given(mockedRapidApiConfiguration.getHost()).willReturn("google-translate1.p.rapidapi.com");
        given(mockedRapidApiConfiguration.getUrl()).willReturn("https://google-translate1.p.rapidapi.com/language/translate/v2");
        given(mockedRapidApiConfiguration.getToken()).willReturn("XXXXX");

    }

    @Test
    void itShouldReturnGoogleTranslateResponseAndConvertToGoogleTranslatorRs() throws IOException {
        //Given
        Request mockRequest = mockRequest();
        Response mockedResponse = mockResponse(mockRequest);
        Call mockedCall = mock(Call.class);
        given(mockedOkHttpClient.newCall(any())).willReturn(mockedCall);
        given(mockedCall.execute()).willReturn(mockedResponse);

        GoogleTranslatorRs mockGoogleTranslatorRs = mockGoogleTranslatorRs();
        given(mockedObjectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(mockGoogleTranslatorRs);

        //When
        Translation translation = underTest.translate("Hello", "es");

        //Then
        assertThat(translation).isNotNull();
        assertThat(translation.getTranslatedText()).isEqualTo("Hola");
    }

    @Test
    void itShouldThrowRunTimeExceptionWhenGoogleTranslateResponseIsNull() throws IOException {
        //Given
        Request mockRequest = mockRequest();
        Response mockedResponse = mockResponse(mockRequest);
        Call mockedCall = mock(Call.class);
        given(mockedOkHttpClient.newCall(any())).willReturn(mockedCall);
        given(mockedCall.execute()).willReturn(mockedResponse);

        GoogleTranslatorRs mockGoogleTranslatorRs = mockGoogleTranslatorRs();
        given(mockedObjectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(mockGoogleTranslatorRs);

        //When
        //Then
        assertThatThrownBy(() -> underTest.translate("Hello", "es"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Response from Google translator is null");

    }

    @Test
    void itShouldThrowRunTimeExceptionWhenParsingJsonFromGoogleTranslateResponse() throws IOException {
        //Given
        Request mockRequest = mockRequest();
        Response mockedResponse = mockResponse(mockRequest);

        Call mockedCall = mock(Call.class);
        given(mockedOkHttpClient.newCall(any())).willReturn(mockedCall);
        given(mockedCall.execute()).willReturn(mockedResponse);

        GoogleTranslatorRs mockGoogleTranslatorRs = mockGoogleTranslatorRs();
        mockGoogleTranslatorRs.setData(null);
        given(mockedObjectMapper.readValue(anyString(), (Class<Object>) any())).willReturn(mockGoogleTranslatorRs);

        //When
        //Then
        assertThatThrownBy(() -> underTest.translate("Hello", "es"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error parsing JSON from Google Translate Response");

    }


    private Response mockResponse(Request mockRequest) {
        return new Response.Builder()
                .request(mockRequest)
                .protocol(Protocol.HTTP_2)
                .body(ResponseBody.create(
                        MediaType.get("application/json; charset=utf-8"), "{}"))
                .message("Hola")
                .code(200)
                .build();
    }

    private Request mockRequest (){
        return new Request.Builder()
                .url("https://some-url.com")
                .build();
    }

    private GoogleTranslatorRs mockGoogleTranslatorRs(){
        GoogleTranslatorRs googleTranslatorRs = new GoogleTranslatorRs();
        Data data = new Data();
        data.setTranslations(List.of(new TranslatedText("Hola")));
        googleTranslatorRs.setData(data);
        return googleTranslatorRs;
    }

}
