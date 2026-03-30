<script setup>
import { computed } from 'vue'
import { useCart } from '../composables/useCart'
import { useQuickView } from '../composables/useQuickView'

const props = defineProps(['isOpen'])
const emit = defineEmits(['close'])

const { state, removeFromCart, updateQuantity, total, totalItems, generateWhatsappLink } = useCart()
const { openQuickView } = useQuickView()

const isEmpty = computed(() => state.items.length === 0)

const itemsToNextPromo = computed(() => {
  const currentCount = totalItems.value
  if (currentCount === 0) return 0
  const remainder = currentCount % 10
  return remainder === 0 ? 0 : 10 - remainder
})

const handleCheckout = () => {
  const link = generateWhatsappLink()
  window.open(link, '_blank')
}
</script>

<template>
  <Transition name="fade">
    <div v-if="isOpen" class="modal-overlay" @click.self="emit('close')">
      <div class="modal-content">
        <header class="modal-header">
          <h3>Sua Cuia (Carrinho)</h3>
          <button class="close-btn" @click="emit('close')">&times;</button>
        </header>

        <div v-if="isEmpty" class="empty-cart">
          <p>Sua cuia está vazia. Adicione alguns stickers para começar!</p>
          <button class="btn-primary" @click="emit('close')">Ver Catálogo</button>
        </div>

        <div v-else class="cart-items">
          <div v-for="item in state.items" :key="item.id" class="cart-item">
            <div class="item-img" @click="openQuickView(item)">
              <img :src="item.imageUrl || ''" :alt="item.name" />
            </div>
            <div class="item-details">
              <h4>{{ item.name }}</h4>
              <div class="item-qty-selector">
                <button class="qty-btn" @click="updateQuantity(item.id, -1)">-</button>
                <span class="qty-value">{{ item.quantity }}</span>
                <button class="qty-btn" @click="updateQuantity(item.id, 1)">+</button>
                <span class="item-price-unit">x R$ {{ item.price.toFixed(2) }}</span>
              </div>
            </div>
            <button class="remove-btn" @click="removeFromCart(item.id)">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18m-2 0v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6m3 0V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"></path></svg>
            </button>
          </div>
        </div>

        <footer v-if="!isEmpty" class="modal-footer">
          <div class="total-section">
            <div class="total-label">
                <span>Total:</span>
                <p v-if="totalItems >= 10" class="promo-badge">
                  Promoção 10 por R$ 25 aplicada!
                </p>
                <p v-else-if="itemsToNextPromo > 0" class="promo-hint">
                  Adicione mais <strong>{{ itemsToNextPromo }}</strong> para o kit de 10 por R$ 25!
                </p>
            </div>
            <span class="total-price">R$ {{ total.toFixed(2) }}</span>
          </div>
          <button class="btn-primary w-full" @click="handleCheckout">
             Finalizar no WhatsApp
          </button>
          <p class="footer-note">Você será redirecionado para o WhatsApp para concluir o pedido.</p>
        </footer>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(8, 28, 21, 0.6);
  backdrop-filter: blur(4px);
  z-index: 1000;
  display: flex;
  justify-content: flex-end;
}

.modal-content {
  background: var(--color-white);
  width: 100%;
  max-width: 480px;
  height: 100%;
  padding: 40px;
  box-shadow: var(--shadow-lg);
  display: flex;
  flex-direction: column;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.close-btn {
  font-size: 2rem;
  line-height: 1;
  color: var(--color-forest-base);
}

.empty-cart {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  gap: 24px;
}

.cart-items {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 32px;
}

.cart-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid var(--color-cream-dark);
}

.item-img {
  width: 64px;
  height: 64px;
  background: var(--color-cream-dark);
  border-radius: var(--radius-sm);
  overflow: hidden;
  cursor: pointer;
  transition: transform var(--transition-fast);
}

.item-img:hover {
  transform: scale(1.05);
}

.item-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item-details {
  flex: 1;
}

.item-details h4 {
  font-size: 1rem;
  margin-bottom: 8px;
}

.item-qty-selector {
  display: flex;
  align-items: center;
  gap: 12px;
}

.qty-btn {
  width: 24px;
  height: 24px;
  border: 1px solid var(--color-cream-dark);
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  transition: all 0.2s;
}

.qty-btn:hover {
  background: var(--color-forest-dark);
  color: white;
  border-color: var(--color-forest-dark);
}

.qty-value {
  font-weight: 600;
  min-width: 20px;
  text-align: center;
}

.item-price-unit {
  font-size: 0.825rem;
  color: var(--color-forest-soft);
  margin-left: 4px;
}

.remove-btn {
  color: #cc0000;
  opacity: 0.6;
  transition: opacity 0.2s;
}

.remove-btn:hover {
  opacity: 1;
}

.modal-footer {
  padding-top: 24px;
  border-top: 1px solid var(--color-cream-dark);
}

.total-section {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  font-size: 1.25rem;
  font-weight: 700;
  margin-bottom: 24px;
  color: var(--color-forest-dark);
}

.total-label {
    display: flex;
    flex-direction: column;
}

.promo-badge {
    font-size: 0.75rem;
    color: var(--color-leaf);
    background: var(--color-forest-dark);
    padding: 2px 8px;
    border-radius: 4px;
    font-weight: 700;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    margin-top: 6px;
    display: inline-block;
}

.promo-hint {
    font-size: 0.8125rem;
    color: var(--color-forest-base);
    font-weight: 500;
    margin-top: 6px;
    font-style: italic;
}

.promo-hint strong {
    color: var(--color-leaf);
    background: var(--color-forest-dark);
    padding: 0 4px;
    border-radius: 2px;
}

.w-full {
  width: 100%;
}

.footer-note {
  font-size: 0.825rem;
  color: var(--color-forest-soft);
  text-align: center;
  margin-top: 12px;
}

/* Transitions */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.fade-enter-active .modal-content, .fade-leave-active .modal-content {
  transition: transform 0.3s ease-out;
}

.fade-enter-from .modal-content {
  transform: translateX(100%);
}

.fade-leave-to .modal-content {
  transform: translateX(100%);
}
</style>
