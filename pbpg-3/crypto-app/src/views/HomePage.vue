<template>
  <!-- IonPage: Container utama untuk satu halaman di Ionic -->
  <IonPage>
    <!-- IonHeader: Bagian header/judul halaman yang tetap di atas -->
    <IonHeader>
      <!-- IonToolbar: Bar untuk menampung title dan action buttons -->
      <IonToolbar class="header-toolbar">
        <!-- IonTitle: Judul halaman, class ion-text-center untuk rata tengah -->
        <IonTitle class="ion-text-center">Crypto Prices</IonTitle>
      </IonToolbar>
    </IonHeader>

    <!-- IonContent: Area konten utama yang bisa di-scroll -->
    <IonContent class="page-bg">
      <!-- Container: Div pembungkus untuk padding dan layout -->
      <div class="container">
        
        <!-- TOMBOL REFRESH -->
        <!-- Tombol untuk memanggil ulang API dan mengambil data terbaru -->
        <IonButton
          expand="block"
          class="refresh-btn"
          @click="fetchTickers"
          :disabled="loading"
        >
          <!-- Tampilkan "Refresh" jika tidak loading, "Loading..." jika sedang loading -->
          <span v-if="!loading">Refresh</span>
          <span v-else>Loading...</span>
        </IonButton>

        <!-- PESAN ERROR -->
        <!-- Div ini hanya muncul jika ada error (v-if="error") -->
        <div v-if="error" class="center error-text">
          {{ error }}
        </div>

        <!-- LIST DATA CRYPTO -->
        <!-- IonList hanya ditampilkan jika tickers sudah terisi (ada data) -->
        <IonList v-if="tickers.length > 0">
          <!-- Loop setiap data crypto dengan v-for -->
          <!-- ticker adalah satu item crypto (Bitcoin, Ethereum, dll) -->
          <IonItem
            v-for="ticker in tickers"
            :key="ticker.id"
            class="crypto-item"
          >
            <IonLabel>
              <!-- Div row: layout horizontal untuk 3 kolom (rank, name, price) -->
              <div class="row">
                
                <!-- KOLOM RANK: Menampilkan peringkat crypto -->
                <div class="col-rank">
                  <div class="label-small">Rank</div>
                  <div class="value-strong">{{ ticker.rank }}</div>
                </div>

                <!-- KOLOM NAME: Menampilkan nama dan simbol crypto -->
                <div class="col-name">
                  <!-- Label nama crypto (misal: Bitcoin) -->
                  <div class="label-small">
                    {{ ticker.name }}
                  </div>
                  <!-- Simbol crypto (misal: BTC) -->
                  <div class="symbol">
                    {{ ticker.symbol }}
                  </div>
                </div>

                <!-- KOLOM PRICE: Menampilkan harga dalam USD -->
                <div class="col-price">
                  <div class="label-small">USD</div>
                  <!-- Harga diformat dengan fungsi formatPrice() -->
                  <div class="value-strong">
                    {{ formatPrice(ticker.price_usd) }}
                  </div>
                </div>
              </div>
            </IonLabel>
          </IonItem>
        </IonList>

        <!-- PESAN KOSONG -->
        <!-- Tampil jika tidak loading, tidak ada data, dan tidak ada error -->
        <div v-if="!loading && tickers.length === 0 && !error" class="center">
          Tidak ada data. Tekan tombol Refresh.
        </div>
      </div>
    </IonContent>
  </IonPage>
</template>

<script setup lang="ts">
// Import fungsi reactive dari Vue
import { ref, onMounted } from 'vue';
// Import komponen-komponen Ionic yang digunakan
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
  IonLabel,
  IonButton,
} from '@ionic/vue';

/**
 * INTERFACE / TIPE DATA
 * Mendefinisikan struktur data yang diterima dari API
 */

// Tipe data untuk satu item cryptocurrency
interface CryptoTicker {
  id: string;           // ID unik crypto (misal: "90")
  symbol: string;       // Simbol crypto (misal: "BTC")
  name: string;         // Nama lengkap (misal: "Bitcoin")
  rank: number | string; // Peringkat market cap
  price_usd: string;    // Harga dalam USD (bentuk string dari API)
}

// Tipe data response dari API Coinlore
interface CoinLoreResponse {
  data: CryptoTicker[]; // Array berisi list crypto
}

/**
 * REACTIVE STATE VARIABLES
 * Variable yang akan otomatis update tampilan ketika nilainya berubah
 */

// Array untuk menyimpan data crypto yang ditampilkan
const tickers = ref<CryptoTicker[]>([]);

// Boolean untuk tracking status loading (true = sedang ambil data)
const loading = ref<boolean>(false);

// String untuk menyimpan pesan error (kosong jika tidak ada error)
const error = ref<string>('');

/**
 * FUNGSI fetchTickers()
 * Fungsi untuk mengambil data cryptocurrency dari API
 * 
 * Cara kerja:
 * 1. Set loading = true (tampilkan status loading di UI)
 * 2. Kosongkan error sebelumnya
 * 3. Panggil API dengan fetch()
 * 4. Jika sukses: simpan data ke tickers
 * 5. Jika error: simpan pesan error
 * 6. Set loading = false (sembunyikan status loading)
 */
const fetchTickers = async () => {
  loading.value = true;  // Mulai loading
  error.value = '';      // Reset error

  try {
    // Panggil API Coinlore
    const res = await fetch('https://api.coinlore.net/api/tickers/');

    // Cek apakah response sukses (status 200-299)
    if (!res.ok) {
      throw new Error('Gagal mengambil data dari server');
    }

    // Parse response JSON menjadi object JavaScript
    const json: CoinLoreResponse = await res.json();

    // Ambil 7 data pertama saja dan simpan ke tickers
    // slice(0, 7) = ambil index 0 sampai 6 (total 7 item)
    tickers.value = json.data.slice(0, 7);
    
  } catch (e: any) {
    // Jika terjadi error, simpan pesan error
    error.value = e?.message ?? 'Terjadi kesalahan saat memuat data';
  } finally {
    // Selalu dijalankan (baik sukses/error), set loading = false
    loading.value = false;
  }
};

/**
 * FUNGSI formatPrice()
 * Fungsi untuk memformat harga agar lebih mudah dibaca
 * 
 * Contoh input: "12345.6789"
 * Output: "12,345.68"
 * 
 * @param price - Harga dalam bentuk string dari API
 * @returns Harga yang sudah diformat dengan separator dan 2 desimal
 */
const formatPrice = (price: string): string => {
  // Konversi string ke number
  const n = Number(price);
  
  // Jika bukan angka valid, return string asli
  if (Number.isNaN(n)) return price;
  
  // Format dengan locale US (pakai koma sebagai thousand separator)
  // minimumFractionDigits: minimal 2 angka desimal
  // maximumFractionDigits: maksimal 2 angka desimal
  return n.toLocaleString('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
};

/**
 * LIFECYCLE HOOK: onMounted()
 * Fungsi yang otomatis dipanggil saat halaman pertama kali dimuat
 * 
 * Di sini kita langsung panggil fetchTickers() agar data crypto
 * langsung ditampilkan begitu user buka halaman
 */
onMounted(() => {
  fetchTickers();
});
</script>

<style scoped>
.page-bg {
  --background: #f7e9b8;
}

ion-list {
  background: transparent;
}

.header-toolbar {
  --background: #f4d03f;
  --color: #2c3e50;
}

ion-title {
  font-weight: 600;
  color: #2c3e50;
}

.container {
  padding: 12px;
}

.refresh-btn {
  margin-bottom: 12px;
}

/* Tengahin teks */
.center {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin-top: 12px;
}

.error-text {
  color: #c0392b;
  font-weight: 600;
}

/* Style item agar mirip kartu di contoh gambar */
.crypto-item {
  --background: #ffffff;
  --color: #2c3e50;
  margin-bottom: 8px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

ion-label {
  color: #2c3e50 !important;
}

/* Layout 3 kolom: rank | name-symbol | price */
.row {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 8px;
  color: #2c3e50;
}

.col-rank {
  width: 50px;
}

.col-name {
  flex: 1;
}

.col-price {
  width: 90px;
  text-align: right;
}

.label-small {
  font-size: 0.7rem;
  text-transform: uppercase;
  color: #7f8c8d;
}

.value-strong {
  font-size: 1rem;
  font-weight: 600;
  color: #2c3e50;
}

.symbol {
  font-size: 0.95rem;
  font-weight: 600;
  color: #2c3e50;
}
</style>
