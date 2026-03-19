import { reactive, computed } from 'vue'

const state = reactive({
  items: []
})

export function useCart() {
  const addToCart = (product) => {
    const existing = state.items.find(i => i.id === product.id)
    if (existing) {
      existing.quantity++
    } else {
      state.items.push({ ...product, quantity: 1 })
    }
  }

  const removeFromCart = (productId) => {
    state.items = state.items.filter(i => i.id !== productId)
  }

  const totalItems = computed(() => state.items.reduce((sum, item) => sum + item.quantity, 0))
  const total = computed(() => state.items.reduce((sum, item) => sum + (item.price * item.quantity), 0))

  const generateWhatsappLink = () => {
    const phoneNumber = '5511999999999' // placeholder
    let text = 'Olá! Gostaria de encomendar os seguintes stickers da Calco.Mania:%0A%0A'
    state.items.forEach(i => {
      text += `- ${i.quantity}x ${i.name} (R$ ${i.price.toFixed(2)})%0A`
    })
    text += `%0ATotal: R$ ${total.value.toFixed(2)}`
    return `https://wa.me/${phoneNumber}?text=${text}`
  }

  return {
    state,
    addToCart,
    removeFromCart,
    totalItems,
    total,
    generateWhatsappLink
  }
}
