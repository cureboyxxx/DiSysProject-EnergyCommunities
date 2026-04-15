package at.uastw.EnergyProducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherResponse {
    private CurrentWeather current;

    public CurrentWeather getCurrent() { return current; }
    public void setCurrent(CurrentWeather current) { this.current = current; }

    public static class CurrentWeather {
        @JsonProperty("is_day")
        private int isDay;

        @JsonProperty("cloud_cover")
        private int cloudCover;

        public int getIsDay() {
            return isDay;
        }

        public void setIsDay(int isDay) {
            this.isDay = isDay;
        }

        public int getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(int cloudCover) {
            this.cloudCover = cloudCover;
        }
    }

}
