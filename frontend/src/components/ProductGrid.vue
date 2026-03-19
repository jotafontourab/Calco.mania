<script setup>
import { ref, onMounted, watch } from 'vue'
import { useCart } from '../composables/useCart'

const props = defineProps(['themeId'])
const { addToCart } = useCart()

const products = ref([])
const loading = ref(true)

const fetchProducts = async () => {
  loading.value = true
  try {
    let url = 'http://localhost:8080/api/stickers'
    if (props.themeId) {
      url += `?themeId=${props.themeId}`
    }
    const response = await fetch(url)
    const data = await response.json()
    products.value = data
  } catch (error) {
    console.error('Error fetching products:', error)
  } finally {
    loading.value = false
  }
}

const handleAdd = (product) => {
  addToCart(product)
}

onMounted(fetchProducts)

// Re-fetch when theme changes
watch(() => props.themeId, fetchProducts)
</script>

<template>
  <section class="products-section">
    <div class="container">
      <div class="section-header">
        <h2>{{ themeId ? 'Filtrando por Temas' : 'Últimos Lançamentos' }}</h2>
        <p>Adesivos premium, resistentes à água, ao tempo e à sua térmica.</p>
      </div>

      <div v-if="loading" class="loading">Buscando stickers...</div>
      
      <div v-else-if="products.length === 0" class="no-products">
        Nenhum sticker encontrado nesta categoria.
      </div>

      <div v-else class="products-grid">
        <div v-for="product in products" :key="product.id" class="product-card">
          <div class="product-image">
            <img v-if="product.imageUrl" :src="product.imageUrl" :alt="product.name" />
            <div v-else class="product-image-placeholder">
              <span>Sticker Preview</span>
            </div>
          </div>
          <div class="product-info">
            <span class="product-theme">{{ product.theme?.name || 'Geral' }}</span>
            <h3>{{ product.name }}</h3>
            <div class="product-footer">
              <span class="product-price">R$ {{ product.price.toFixed(2) }}</span>
              <button class="btn-primary btn-sm" @click="handleAdd(product)">
                <span>+</span> Cuia
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.products-section {
  padding: 80px 0;
}
.section-header {
  text-align: center;
  margin-bottom: 48px;
}
.section-header p {
  color: var(--color-forest-base);
  font-size: 1.125rem;
}
.loading, .no-products {
  text-align: center;
  padding: 40px;
  color: var(--color-forest-light);
  font-style: italic;
}
.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 32px;
}
.product-card {
  background-color: var(--color-white);
  border-radius: var(--radius-md);
  overflow: hidden;
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-normal);
}
.product-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-md);
}
.product-image {
  height: 240px;
  background-color: var(--color-cream-dark);
  border-bottom: 1px solid var(--color-cream);
  overflow: hidden;
}
.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform var(--transition-normal);
}
.product-card:hover .product-image img {
  transform: scale(1.1);
}
.product-image-placeholder {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-leather-base);
  font-family: var(--font-serif);
  font-style: italic;
}
.product-info {
  padding: 24px;
}
.product-theme {
  font-size: 0.875rem;
  color: var(--color-leaf);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  font-weight: 600;
  margin-bottom: 8px;
  display: block;
}
.product-info h3 {
  font-family: var(--font-sans);
  font-size: 1.25rem;
  color: var(--color-forest-dark);
  margin-bottom: 16px;
}
.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.product-price {
  font-weight: 700;
  font-size: 1.125rem;
  color: var(--color-forest-base);
}
.btn-sm {
  padding: 8px 16px;
  font-size: 0.875rem;
}
</style>
