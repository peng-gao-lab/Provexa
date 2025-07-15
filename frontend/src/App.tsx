import { Depths, Stack } from '@fluentui/react';
import React from 'react';
import './App.css';
import CodeBlock from './components/CodeBlock';

const App: React.FC = () => {
  const [blocks, setBlocks] = React.useState(1)
  const [inputs, setInputs] = React.useState(0)
  const [executing, setExecuting] = React.useState(false)

  const updateExecutionState = React.useCallback((index: number, executing: boolean) => {
    setExecuting(executing)
    setInputs(inputs + 1)
    if (index === blocks - 1) {
      setBlocks(blocks + 1)
    }
  }, [blocks, inputs])

  return <>
    <div className="header-container">
      <img src="/provexa-logo.png" alt="Provexa Logo" className="logo" />
      <h1 className="header-title">Provexa WebUI</h1>
    </div>
    <div id="app" style={{ boxShadow: Depths.depth8 }}>
      <Stack tokens={{childrenGap: 20}}>
        {[...Array(blocks)].map((_, i) => (
          <CodeBlock
            index={i}
            inputs={inputs}
            executing={executing}
            updateExecutionState={updateExecutionState}
            key={i} />
        ))}
      </Stack>
    </div>
  </>;
}

export default App;
