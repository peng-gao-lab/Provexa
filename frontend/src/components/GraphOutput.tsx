import React from 'react';
import Viz from "viz.js";
import { Module, render } from "viz.js/full.render.js";
import { MapInteractionCSS } from "react-map-interaction";


type GraphOutputProps = {
  graph: string
}

const GraphOutput: React.FC<GraphOutputProps> = (props) => {
  const [element, updateElement] = React.useState("")
  React.useEffect(() => {
    updateElement("")
    const viz = new Viz({ Module, render })
    const f = async () => {
      const el: string = await viz.renderString(props.graph)
      updateElement(el)
    }
    f().catch(console.error)
  }, [props]);

  return (
    <div className="graph">
      {element && (
        <MapInteractionCSS>
            <div dangerouslySetInnerHTML={{ __html: element }}></div>
        </MapInteractionCSS>
      )}
    </div>
  )
}

export default GraphOutput
