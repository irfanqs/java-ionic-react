<template>
  <IonPage>
    <IonHeader>
      <IonToolbar color="primary" class="header-toolbar">
        <IonTitle class="ion-text-center">Prakiraan Cuaca Jakarta</IonTitle>
      </IonToolbar>
    </IonHeader>

    <IonContent class="ion-padding" :style="{ '--background': 'linear-gradient(to bottom, #e3f2fd, #ffffff)' }">
      <div class="container">
        <!-- Loading -->
        <div v-if="loading" class="center">
          <IonSpinner />
          <p>Memuat data cuaca...</p>
        </div>

        <!-- Error -->
        <div v-else-if="error" class="center">
          <IonText color="danger">
            <p>{{ error }}</p>
          </IonText>
          <IonButton @click="fetchWeather" size="small">Coba Lagi</IonButton>
        </div>

        <!-- List Data -->
        <IonList v-else>
          <IonItem v-for="(t, index) in times" :key="t">
            <IonLabel>
              <h2 class="time">{{ formatTime(t) }}</h2>
              <p class="temp">
                {{ temperatures[index] }} °C
              </p>
            </IonLabel>
          </IonItem>
        </IonList>
      </div>
    </IonContent>
  </IonPage>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
  IonLabel,
  IonSpinner,
  IonText,
  IonButton,
} from '@ionic/vue';

interface WeatherResponse {
  hourly: {
    time: string[];
    temperature_2m: number[];
  };
}

const loading = ref<boolean>(true);
const error = ref<string>('');
const times = ref<string[]>([]);
const temperatures = ref<number[]>([]);

const fetchWeather = async () => {
  loading.value = true;
  error.value = '';

  try {
    const res = await fetch(
      'https://api.open-meteo.com/v1/forecast?latitude=-6.2&longitude=106.8&hourly=temperature_2m&timezone=Asia%2FJakarta'
    );

    if (!res.ok) {
      throw new Error('Gagal mengambil data dari server');
    }

    const data: WeatherResponse = await res.json();

    times.value = data.hourly.time;
    temperatures.value = data.hourly.temperature_2m;
  } catch (e: any) {
    error.value = e?.message ?? 'Terjadi kesalahan saat memuat data';
  } finally {
    loading.value = false;
  }
};

const formatTime = (iso: string): string => {
  return new Date(iso).toLocaleString('id-ID', {
    weekday: 'short',
    hour: '2-digit',
    minute: '2-digit',
  });
};

onMounted(() => {
  fetchWeather();
});
</script>

<style scoped>
.container {
  padding: 8px 12px;
}

.center {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 24px;
  gap: 8px;
}

.time {
  font-weight: 600;
  color: #1976d2;
}

.temp {
  font-size: 1.1rem;
  color: #0d47a1;
  font-weight: 500;
}

ion-content {
  --background: linear-gradient(180deg, #e3f2fd 0%, #ffffff 100%);
}

.header-toolbar {
  --background: #42a5f5;
}

ion-title {
  color: white;
  font-weight: 600;
}

ion-list {
  background: transparent;
}

ion-item {
  --background: rgba(255, 255, 255, 0.8);
  --border-color: #bbdefb;
  margin-bottom: 8px;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(66, 165, 245, 0.15);
}

ion-item:hover {
  --background: rgba(255, 255, 255, 0.95);
}
</style>
