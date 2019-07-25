package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalNumberInputRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class VerticalNumberInputBuilder(
        val height: Int,
        private var initialValue: Int = 0,
        private var maxValue: Int = Int.MAX_VALUE,
        override val props: CommonComponentProperties<NumberInput> = CommonComponentProperties(
                componentRenderer = DefaultVerticalNumberInputRenderer()))
    : BaseComponentBuilder<NumberInput, VerticalNumberInputBuilder>() {

    fun withInitialValue(value: Int) = also {
        this.initialValue = value
    }

    fun withMaxValue(value: Int) = also {
        this.maxValue = value
        contentSize = contentSize
                .withHeight(max(this.maxValue.toString().length + 1, height))
                .withWidth(1)
    }

    override fun build(): NumberInput = DefaultVerticalNumberInput(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialValue = initialValue,
                maxValue = maxValue,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<NumberInput>))

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder(height: Int) = VerticalNumberInputBuilder(height = height)
    }
}
