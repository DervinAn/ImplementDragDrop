package com.example.implementdragdrop

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.implementdragdrop.ui.theme.ImplementDragDropTheme
import kotlin.random.Random

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DragAndDropBoxes(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val boxCount = 5
        var dragDropIndex by remember {
            mutableStateOf(0)
        }
        val colors = listOf(
            Color(0xFF051D40),
            Color(0xFF0995CC),
            Color(0xFF11ADF8),
            Color(0xFF1B4B63),
            Color(0xFF153F53)
        )
        val colors2 = remember {

        }

        repeat(boxCount){ index->
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(colors[index])
                .dragAndDropTarget(
                    shouldStartDragAndDrop = { event ->
                        event
                            .mimeTypes()
                            .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    },
                    target = remember {
                        object : DragAndDropTarget {
                            override fun onDrop(event: DragAndDropEvent): Boolean {
                                val text = event.toAndroidDragEvent().clipData
                                    ?.getItemAt(0)?.text
                                println("Drag data was $text")

                                dragDropIndex = index
                                return true
                            }

                        }
                    }
                ),
                contentAlignment = Alignment.Center
            ){
                this@Column.AnimatedVisibility(
                    visible = index == dragDropIndex,
                    enter = scaleIn()+ fadeIn(),
                    exit = scaleOut() + fadeOut()
                ) {
                    val colorb =  remember {
                        mutableStateOf(false)
                    }
                        Text(
                            text ="Vortexcraft",
                            fontSize = 40.sp,
                            color = if(colorb.value) Color.Green else Color(0xFF464DCC),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .dragAndDropSource {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            colorb.value = !colorb.value
                                            startTransfer(
                                                transferData = DragAndDropTransferData(
                                                    clipData = ClipData.newPlainText(
                                                        "text",
                                                        "Vortexcraft"
                                                    )
                                                )
                                            )
                                        }
                                    )
                                }
                        )
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ImplementDragDropTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DragAndDropBoxes(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}