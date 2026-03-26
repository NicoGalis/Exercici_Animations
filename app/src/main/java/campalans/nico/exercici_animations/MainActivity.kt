package campalans.nico.exercici_animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimationsController()
            }
        }
    }
}

@Composable
fun AnimationsController() {
    var showAnimations by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (showAnimations) 180f else 0f,
        animationSpec = tween(600)
    )

    // ÚNIC BOTÓ
    Button(onClick = { showAnimations = !showAnimations }) {
        Text(
            if (showAnimations) "Amagar animacions" else "Mostrar animacions"
        )
    }

    Spacer(Modifier.height(20.dp))

    // MOSTRA O AMAGA TOTES LES ANIMACIONS
    AnimatedVisibility(visible = showAnimations) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            RotatingCircleWithName()

            Spacer(Modifier.height(30.dp))

            TranslationAnimation()
        }
    }
}

@Composable
fun RotatingCircleWithName() {

    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val backgroundColor by infiniteTransition.animateColor(
        initialValue = Color.White,
        targetValue = Color.Black,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val textColor by infiniteTransition.animateColor(
        initialValue = Color.Black,
        targetValue = Color.Transparent,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .size(180.dp)
            .graphicsLayer { rotationZ = rotation }
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nico",
            color = textColor,
            fontSize = 22.sp
        )
    }
}

@Composable
fun TranslationAnimation() {
    var moved by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            moved = !moved
            delay(1500)
        }
    }

    val offsetX by animateDpAsState(
        targetValue = if (moved) 200.dp else 0.dp,
        animationSpec = infiniteRepeatable(
            tween(2000),
            RepeatMode.Reverse)
    )

    Box(
        modifier = Modifier
            .offset(x = offsetX)
            .size(80.dp)
            .background(Color.Blue)
    )
}
