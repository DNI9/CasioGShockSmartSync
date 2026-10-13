package org.avmedia.gshockGoogleSync.ui.actions
import androidx.hilt.navigation.compose.hiltViewModel

import AppTextLarge
import AppSwitch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.avmedia.gshockGoogleSync.R
import org.avmedia.gshockGoogleSync.ui.common.AppCard
import org.avmedia.gshockGoogleSync.ui.common.AppIconFromResource
import org.avmedia.gshockGoogleSync.utils.Utils

@Composable
fun PhotoView(
    onUpdate: (ActionContainer.PhotoAction) -> Unit,
    actionsViewModel: ActionsViewModel = hiltViewModel()
) {
    data class ViewState(
        val isEnabled: Boolean,
        val cameraOrientation: ActionContainer.CameraOrientation,
        val action: ActionContainer.PhotoAction
    )

    val actions by actionsViewModel.actions.collectAsState()
    val photoAction = remember {
        actionsViewModel.getAction(ActionContainer.PhotoAction::class.java)
    }

    var viewState by remember {
        mutableStateOf(
            ViewState(
                isEnabled = photoAction.enabled,
                cameraOrientation = photoAction.cameraOrientation,
                action = photoAction
            )
        )
    }

    AppCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.padding(4.dp)) {
                AppIconFromResource(resourceId = R.drawable.camera)
            }

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextLarge(
                    text = Utils.shortenString(
                        stringResource(id = R.string.take_photo),
                        15
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.weight(1f))

                val cameraOptions = listOf(
                    ActionContainer.CameraOrientation.FRONT to stringResource(id = R.string.front_cam),
                    ActionContainer.CameraOrientation.BACK to stringResource(id = R.string.back_cam)
                )
                val selectedIndex = if (viewState.cameraOrientation == ActionContainer.CameraOrientation.FRONT) 0 else 1

                org.avmedia.gshockGoogleSync.ui.common.SegmentedPillControl(
                    items = cameraOptions,
                    selectedIndex = selectedIndex,
                    onSelected = { idx ->
                        val newOrientation = cameraOptions[idx].first
                        val newState = viewState.copy(cameraOrientation = newOrientation)
                        viewState = newState
                        viewState.action.cameraOrientation = newOrientation
                        onUpdate(viewState.action)
                    },
                    itemLabel = { it.second },
                    modifier = Modifier.width(140.dp)
                )
            }

            AppSwitch(
                checked = viewState.isEnabled,
                onCheckedChange = { newValue: Boolean ->
                    val newState = viewState.copy(isEnabled = newValue)
                    viewState = newState
                    viewState.action.enabled = newValue
                    onUpdate(viewState.action)
                },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
